import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.services.*
import java.util.concurrent.CompletableFuture

class KtLanguageServer : LanguageServer, LanguageClientAware {
    private var client: LanguageClient? = null
    override fun initialize(params: InitializeParams?): CompletableFuture<InitializeResult> {
        val capabilities = ServerCapabilities()
        return CompletableFuture.completedFuture(InitializeResult(capabilities))
    }

    override fun shutdown(): CompletableFuture<Any> {
        return CompletableFuture.completedFuture(null)
    }

    override fun exit() {
        println("language server exist..")
    }

    override fun getTextDocumentService(): TextDocumentService {
        return KtTextDocumentService()
    }

    override fun getWorkspaceService(): WorkspaceService {
        return KtWorkspaceService()
    }

    override fun connect(client: LanguageClient?) {
        println("connect with...")
        this.client = client
    }
}

class KtTextDocumentService : TextDocumentService {
    override fun didOpen(params: DidOpenTextDocumentParams?) {
        TODO("Not yet implemented")
    }

    override fun didChange(params: DidChangeTextDocumentParams?) {
        TODO("Not yet implemented")
    }

    override fun didClose(params: DidCloseTextDocumentParams?) {
        TODO("Not yet implemented")
    }

    override fun didSave(params: DidSaveTextDocumentParams?) {
        TODO("Not yet implemented")
    }
}

class KtWorkspaceService() : WorkspaceService {
    override fun didChangeConfiguration(params: DidChangeConfigurationParams?) {
        TODO("Not yet implemented")
    }

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams?) {
        TODO("Not yet implemented")
    }

}
