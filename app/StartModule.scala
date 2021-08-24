import com.google.inject.AbstractModule
import io.sentry.{Sentry, SentryOptions}
import play.api.{Configuration, Environment}

class StartModule(environment: Environment, config: Configuration) extends AbstractModule {

  private val env = config.get[String]("environment")

  override def configure() = {
    val dsn = if (env == "local") "" else config.get[String]("sentry.dsn")
    Sentry.init((options: SentryOptions) => {
      options.setDsn(dsn)
      options.setEnvironment(env)
      options.setDebug(true)
    })
  }
}
