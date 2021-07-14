import * as vscode from 'vscode';
import * as path from "path";

import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient/node';
import { RevealOutputChannelOn } from 'vscode-languageclient';


let client: LanguageClient;

export function activate(context: vscode.ExtensionContext) {
  console.log("kotlin language server protocol activate")
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
    outputChannel: vscode.window.createOutputChannel("Kotlin"),
    revealOutputChannelOn: RevealOutputChannelOn.Never,
  }
  let serverOptions: ServerOptions = {
    command: context.asAbsolutePath(path.join('script', 'launch_in_mac.sh')),
    args: [],
    options: {
      cwd: vscode.workspace.workspaceFolders?.[0]?.uri?.fsPath,
    }
  };

  let disposable = new LanguageClient("java", "Java Language Server", serverOptions, clientOptions).start();
  console.log("server start....");
  context.subscriptions.push(disposable);
}

export function deactivate(): Thenable<void> | undefined {
  if (!client) {
    return undefined;
  }
  return client.stop();
}
