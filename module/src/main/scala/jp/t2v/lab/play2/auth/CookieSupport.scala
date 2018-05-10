package jp.t2v.lab.play2.auth

import play.api.libs.Crypto
import play.api.mvc.{Result, Cookie}

@deprecated("It will be deleted. use TokenAccessor instead of this", since = "0.13.1")
trait CookieSupport { self: AuthConfig =>

  def verifyHmac(cookie: Cookie): Option[String] = {
    val (hmac, value) = cookie.value.splitAt(40)
    if (HMACGenerator.createHMAC(value) == hmac) Some(value) else None
  }

  def bakeCookie(token: String)(result: Result): Result = {
    val value = HMACGenerator.createHMAC(token) + token
    val maxAge = if (isTransientCookie) None else Some(sessionTimeoutInSeconds)
    result.withCookies(Cookie(cookieName, value, maxAge, cookiePathOption, cookieDomainOption, cookieSecureOption, cookieHttpOnlyOption))
  }

}
