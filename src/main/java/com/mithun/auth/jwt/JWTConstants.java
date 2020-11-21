package com.mithun.auth.jwt;

public interface JWTConstants {
	public static String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh";
	public static String HEADER_NAME = "Authorization";
	public static Long EXPIRATION_TIME = 1000L * 60 * 30;
	public static String SECRET = "SecretKeyToGenJWTs";
	public static String TOKEN_PREFIX = "Bearer ";
	public static String HEADER_STRING = "Authorization";
	public static String SIGN_UP_URL = "/signup";
}