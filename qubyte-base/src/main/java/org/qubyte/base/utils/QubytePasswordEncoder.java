package org.qubyte.base.utils;

import org.apache.commons.codec.binary.Base64;
import org.qubyte.base.logger.BaseLogger;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author Alok kumar
 *
 */
public class QubytePasswordEncoder implements PasswordEncoder {

	private final Logger logger = BaseLogger.getLogger(QubytePasswordEncoder.class);

	static final int MIN_LOG_ROUNDS = 4;
	static final int MAX_LOG_ROUNDS = 31;

	public static final String SECRET_KEY = "BAtteryAppKEYEncryption";

	public QubytePasswordEncoder() {
		this(-1);
	}

	/**
	 * @param strength the log rounds to use, between 4 and 31
	 */
	public QubytePasswordEncoder(int strength) {
		this(strength, null);
	}

	/**
	 * @param version the version of bcrypt, can be 2a,2b,2y
	 */
	public QubytePasswordEncoder(BCryptVersion version) {
		this(version, null);
	}

	/**
	 * @param version the version of bcrypt, can be 2a,2b,2y
	 * @param random  the secure random instance to use
	 */
	public QubytePasswordEncoder(BCryptVersion version, SecureRandom random) {
		this(version, -1, random);
	}

	/**
	 * @param strength the log rounds to use, between 4 and 31
	 * @param random   the secure random instance to use
	 */
	public QubytePasswordEncoder(int strength, SecureRandom random) {
		this(BCryptVersion.$2A, strength, random);
	}

	/**
	 * @param version  the version of bcrypt, can be 2a,2b,2y
	 * @param strength the log rounds to use, between 4 and 31
	 */
	public QubytePasswordEncoder(BCryptVersion version, int strength) {
		this(version, strength, null);
	}

	/**
	 * @param version  the version of bcrypt, can be 2a,2b,2y
	 * @param strength the log rounds to use, between 4 and 31
	 * @param random   the secure random instance to use
	 */
	public QubytePasswordEncoder(BCryptVersion version, int strength, SecureRandom random) {
		if (strength != -1 && (strength < MIN_LOG_ROUNDS || strength > MAX_LOG_ROUNDS)) {
			throw new IllegalArgumentException("Bad strength");
		}
	}

	/**
	 * Encrypt a string using AES encryption algorithm.
	 *
	 * @param rawPassword the password to be encrypted
	 * @return the encrypted string
	 */
	@Override
	public String encode(CharSequence rawPassword) {

		try {
			SecretKey secretKey = setKey(SECRET_KEY);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return new String(new Base64().encode(cipher.doFinal(String.valueOf(rawPassword).getBytes("UTF-8"))));

		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	/**
	 * Decrypt a string with AES encryption algorithm.
	 *
	 * @param encodedPassword the data to be decrypted
	 * @return the decrypted string
	 */
	public String decrypt(String encodedPassword) {

		try {
			SecretKey secretKey = setKey(SECRET_KEY);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.decodeBase64(encodedPassword)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	/**
	 * Generate a new encryption key.
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {

		if (encodedPassword == null || encodedPassword.length() == 0) {
			logger.warn("Empty encoded password");
			return false;
		}
		String pass = decrypt(encodedPassword);

		return String.valueOf(rawPassword).equals(pass);
	}

	private SecretKey setKey(String myKey) {

		MessageDigest sha = null;
		try {
			byte[] key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-256");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			return new SecretKeySpec(key, "AES");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
