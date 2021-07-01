import org.eclipse.lsp4j.launch.LSPLauncher

fun main(args: Array<String>) {
    val server = KtLanguageServer()
    val launcher = LSPLauncher.createServerLauncher(server, System.`in`, System.out)
    server.connect(launcher.remoteProxy)
    launcher.startListening()
}
