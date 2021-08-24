//import com.google.inject.AbstractModule
//import io.sentry.Sentry
//import net.codingwell.scalaguice.ScalaModule
//import play.api.Configuration
//
//import javax.inject._
//
//@Singleton
//class ApplicationStart @Inject() (config: Configuration) {
//
//  val environment = config.get[String]("environment")
//
//  def setupSentry() = {
//    val sentryDSN = config.get[String]("sentry.dsn")
//
//    val environmentQS = s"environment=${environment}"
//    val stacktracePackageQS = "stacktrace.app.packages=services,controllers,models,dao"
//    val fullDSN = s"${sentryDSN}?${environmentQS}&${stacktracePackageQS}"
//
//    Sentry.init(fullDSN)
//  }
//
//  if (environment != "local") {
//    setupSentry()
//  }
//}
//
///**
//  * Sets up custom components for Play.
//  *
//  * https://www.playframework.com/documentation/latest/ScalaDependencyInjection
//  */
//class Module extends AbstractModule with ScalaModule {
//
//  override def configure() = {
//    bind(classOf[ApplicationStart]).asEagerSingleton()
//  }
//}
