import * as vscode from 'vscode';
import * as path from "path";

import { LanguageClient, LanguageClientOptions , ServerOptions, TransportKind} from 'vscode-languageclient/node';


let client: LanguageClient;

export function activate(context: vscode.ExtensionContext) {
  console.log("kotlin language server protocol activate")
  let clientOptions : LanguageClientOptions = {
    documentSelector: [{scheme: "file", language: 'kotlin'}],
    synchronize: {
      configurationSection: "kotlin",
      fileEvents: [
        vscode.workspace.createFileSystemWatcher("**/*.java"),
        vscode.workspace.createFileSystemWatcher("**/pom.xml")
      ]
    },
    outputChannelName: "kotlin",
    revealOutputChannelOn: 4
  }
  let serverModule = context.asAbsolutePath(
		path.join('script', 'launch_in_mac.sh')
	);
  let debugOptions = { execArgv: ['--nolazy', '--inspect=6009'] };
  let serverOptions: ServerOptions = {
    run: { module:serverModule ,transport: TransportKind.ipc },
    debug: {
			module: serverModule,
			transport: TransportKind.ipc,
			options: debugOptions
		}
  };

  let disposable = new LanguageClient("java", "Java Language Server", serverOptions, clientOptions).start();
  context.subscriptions.push(disposable);
}

export function deactivate(): Thenable<void> | undefined {
  if (!client) {
    return undefined;
  }
  return client.stop();
}
