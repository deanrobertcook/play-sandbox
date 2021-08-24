import com.google.inject.AbstractModule
import io.sentry.{Sentry, SentryOptions}
import play.api.{Configuration, Environment}

class StartModule(environment: Environment, config: Configuration) extends AbstractModule {

  private val env = config.get[String]("environment")

  def setupSentry() = {
    println(s"WTF: ${Sentry.isEnabled}")
    Sentry.init((options: SentryOptions) => {
      options.setDsn(config.get[String]("sentry.dsn"))
      options.setEnvironment(env)
    })
  }

  override def configure() = {
    println("StartModule#configure")
    if (env != "local") {
      setupSentry()
    }
  }
}
