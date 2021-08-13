import * as vscode from 'vscode';

import { LanguageClient, LanguageClientOptions, ServerOptions, StreamInfo } from 'vscode-languageclient/node';
import { RevealOutputChannelOn } from 'vscode-languageclient';
import * as net from 'net';
import * as child_process from 'child_process';
import * as path from 'path';


let client: LanguageClient;

export async function activate(context: vscode.ExtensionContext) {
  const outputChannel = vscode.window.createOutputChannel("Kotlin")
  let clientOptions: LanguageClientOptions = {
    documentSelector: [{ scheme: "file", language: 'kotlin' }],
    synchronize: {
      configurationSection: "kotlin",
      fileEvents: [
        vscode.workspace.createFileSystemWatcher("**/*.kt"),
        vscode.workspace.createFileSystemWatcher("**/*.kts"),
        vscode.workspace.createFileSystemWatcher("**/pom.xml")
      ]
    },
    progressOnInitialization: true,
    outputChannel: outputChannel,
    revealOutputChannelOn: RevealOutputChannelOn.Never,
  }
  const tcpPort = 5005;
  const executeCommand = context.asAbsolutePath(path.join("script", "launch_in_mac.sh"))
  const options = { outputChannel, executeCommand, tcpPort }
  let serverOptions: ServerOptions;
  serverOptions = () => createTcpServer(options)
  let languageClient = new LanguageClient("kotlin", "Kotlin Language Server", serverOptions, clientOptions);
  let disposable = languageClient.start();
  console.log("server start....");
  context.subscriptions.push(disposable);

  await languageClient.onReady();
}

export function createTcpServer(options: {
  outputChannel: vscode.OutputChannel,
  executeCommand: string,
  tcpPort: number,
}): Promise<StreamInfo> {
  return new Promise((resolve, reject) => {
    console.log("start tcp server");
    const server = net.createServer(socket => {
      server.close();
      console.log("close socket");
      resolve({ reader: socket, writer: socket });
    })
    // const listenOptions: ListenOptions = { host: 'localhost', port: options.tcpPort }
    server.listen(options.tcpPort, () => {
      const tcpPort = (server.address() as net.AddressInfo).port.toString();
      const proc = child_process.spawn(options.executeCommand, []);
      console.log(`execute: ${options.executeCommand}, port: ${tcpPort}`)

      const outputCallback = data => options.outputChannel.append(`${data}`)
      proc.stdout.on("data", outputCallback);
      proc.stderr.on("data", outputCallback);
      proc.on("exit", (code, sig) => options.outputChannel.appendLine(`server exited, code: ${code}, signal: ${sig}`));
    });
    server.on("error", e => {
      reject(e);
      console.log(JSON.stringify(e));
    });
  });
}

export function deactivate(): Thenable<void> | undefined {
  if (!client) {
    return undefined;
  }
  return client.stop();
}
