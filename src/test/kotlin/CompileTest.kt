import com.intellij.openapi.util.Disposer
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CommonConfigurationKeys
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CompileTest {
    @Test
    fun `should create a valid exp with 1 plus 1`() {
        val expression: KtExpression = KtPsiFactory(initEnv().project).createExpression("1 + 1")
        assertThat(expression.isValid).isTrue
    }

    @Test
    fun `eval a piece of kotlin code with 1 plus 1`() {
        assertThrows<AssertionError> {
            KtPsiFactory(initEnv().project).createExpression(" + 1")
        }
    }

    @Test
    fun `analysis import list with a piece of kotlin code`() {
        val text = "import java.time.LocalDateTime"
        val ktFile = KtPsiFactory(initEnv().project).createFile(
            "HelloWorld.kts",
            text,
        )
        assertThat(ktFile.importList?.imports?.size).isGreaterThan(0)
    }

    @Test
    fun `analysis import list with a piece of kotlin none import code`() {
        val text = "val a = 1"
        val ktFile = KtPsiFactory(initEnv().project).createFile(
            "HelloWorld.kts",
            text,
        )
        assertThat(ktFile.importList?.imports?.size).isEqualTo(0)
    }

    private fun initEnv(): KotlinCoreEnvironment {
        val config = CompilerConfiguration().apply {
            this.put(CommonConfigurationKeys.MODULE_NAME, "test-module")
            this.put(
                CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
                PrintingMessageCollector(
                    System.err,
                    MessageRenderer.PLAIN_FULL_PATHS,
                    K2JVMCompilerArguments().verbose,
                )
            )
        }
        return KotlinCoreEnvironment.createForProduction(
            Disposer.newDisposable(),
            config,
            EnvironmentConfigFiles.JVM_CONFIG_FILES,
        )
    }
}
