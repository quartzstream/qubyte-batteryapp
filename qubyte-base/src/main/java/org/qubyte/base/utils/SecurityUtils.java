package org.qubyte.base.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.qubyte.base.logger.BaseLogger;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Alok kumar
 *
 */
public class SecurityUtils {

	private static Logger logger = BaseLogger.getLogger(SecurityUtils.class);

	public static final String SECRET_KEY = "KEYFORENCRYPTION";

	public static String cleanXSS(String value) {

		if (value == null) {
			return value;
		}
		value = value.replaceAll("<script", "");
		value = value.replaceAll("</script>", "");
		value = value.replaceAll("<iframe", "");
		value = value.replaceAll("</iframe>", "");
		value = value.replaceAll("<img", "");
		value = value.replaceAll("</img>", "");
		value = value.replaceAll("<pre", "");
		value = value.replaceAll("</pre>", "");
		value = value.replaceAll("alert", "");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]javascript:(.)[\\\"\\\']", "\"\"");
		value = value.replaceAll("(?i)<script.?>.?<script.*?>", "");
		value = value.replaceAll("(?i)<script.?>.?</script.*?>", "");
		value = value.replaceAll("(?i)<.?javascript:.?>.?</.?>", "");
		value = value.replaceAll("(?i)<.?\\s+on.?>.?</.?>", "");
		value = value.replaceAll("console.log", "");
		value = value.replace("<javascript", "");
		value = value.replace("</javascript>", "");
		value = value.replaceAll("<", "").replaceAll(">", "");

		return value;
	}

	public static boolean checkEmbeddedObjectInXlsFile(File file) {

		boolean isEmbeddedObjectPresent = true;

		POIFSFileSystem fs = null;
		HSSFWorkbook workbook = null;
		FileInputStream fin = null;

		try {
			fin = new FileInputStream(file);
			fs = new POIFSFileSystem(fin);
			workbook = new HSSFWorkbook(fs);
			isEmbeddedObjectPresent = workbook.getAllEmbeddedObjects().size() > 0 ? true : false;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error occured in SecurityUtils.checkEmbeddedObjectInXlsFile ", e);

		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("Error occured in closing the fileInputStream ", e);
				}
			}
		}
		return isEmbeddedObjectPresent;
	}

	public static boolean validateXSS(String intialValue) {

		if (intialValue == null) {
			return true;
		}

		String value = intialValue;

		value = value.replaceAll("<script", "");
		value = value.replaceAll("</script>", "");
		value = value.replaceAll("<iframe", "");
		value = value.replaceAll("</iframe>", "");
		value = value.replaceAll("<img", "");
		value = value.replaceAll("</img>", "");
		value = value.replaceAll("<pre", "");
		value = value.replaceAll("</pre>", "");
		value = value.replaceAll("alert", "");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]javascript:(.)[\\\"\\\']", "\"\"");
		value = value.replaceAll("(?i)<script.?>.?<script.*?>", "");
		value = value.replaceAll("(?i)<script.?>.?</script.*?>", "");
		value = value.replaceAll("(?i)<.?javascript:.?>.?</.?>", "");
		value = value.replaceAll("(?i)<.?\\s+on.?>.?</.?>", "");
		value = value.replaceAll("console.log", "");
		value = value.replace("<javascript", "");
		value = value.replace("</javascript>", "");
		value = value.replaceAll("<", "").replaceAll(">", "");

		return value.equalsIgnoreCase(intialValue);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Object> T prepareCleanXSSDomain(T payReq) {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> map = objectMapper.convertValue(payReq, Map.class);
		Map<String, Object> mapNew = new HashMap();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof HashMap) {
				HashMap hashMap = prepareCleanXSSDomain((HashMap) entry.getValue());
				mapNew.put(entry.getKey(), hashMap);
			} else if (entry.getValue() instanceof List) {
				List list = handleArrays((List) entry.getValue());
				mapNew.put(entry.getKey(), list);
			} else {
				String val = entry.getValue() == null ? null : entry.getValue().toString();
				mapNew.put(entry.getKey(), SecurityUtils.cleanXSS(val));
			}
		}

		return (T) objectMapper.convertValue(mapNew, payReq.getClass());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List handleArrays(List listOrg) {

		List list = new ArrayList();
		if (listOrg == null) {
			return listOrg;
		} else {
			for (Object itemValue : listOrg) {
				if (itemValue instanceof HashMap) {
					HashMap hashMap = prepareCleanXSSDomain((HashMap) itemValue);
					list.add(hashMap);
				} else if (itemValue instanceof List) {
					List lst = handleArrays((List) itemValue);
					list.add(lst);
				} else {
					String val = itemValue == null ? null : itemValue.toString();
					list.add(SecurityUtils.cleanXSS(val));
				}
			}
		}
		return list;
	}

	public static String decrypt(String strToDecrypt, String secret) {

		try {
			SecretKey secretKey = setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public static String encrypt(String strToEncrypt, String secret) {

		try {
			SecretKey secretKey = setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return new String(new Base64().encode(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));

		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	private static SecretKey setKey(String myKey) {

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

	public static void main(String args[]) {

		System.out.println(encrypt("61665", SECRET_KEY));
		System.out.println(encrypt("123456", SECRET_KEY));
		System.out.println(decrypt("pPoZp8KzsI8AiT+w115fWw==", SECRET_KEY));
		System.out.println(decrypt("xzos+pj8Ae0b3HbvSUdJAg==", SECRET_KEY));
	}

	public static String removeChassisSpecialCharactersForVrn(String val) {

		if (val == null || val.length() < 1) {
			return val;
		}

		val = val.replaceAll("[^a-zA-Z0-9]", "");
		// val = val.replaceAll("[ ]", "");

		return val;
	}

	public static String encryptV2(String strToEncrypt, String secret) {

		try {

			SecretKey secretKey = setKeyV2(secret);
			IvParameterSpec iv = new IvParameterSpec(new byte[16]);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

			byte[] destination = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

			return new String(new Base64().encode(destination));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decryptV2(String strToDecrypt, String secret) {

		try {
			SecretKey secretKey = setKeyV2(secret);
			byte[] arr = Base64.decodeBase64(strToDecrypt);
			IvParameterSpec iv = new IvParameterSpec(new byte[16]);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			return new String(cipher.doFinal(arr));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String decryptV3(String strToDecrypt, String secret) {

		try {
			SecretKey secretKey = setKeyV2(secret);
			byte[] arr = Base64.decodeBase64(strToDecrypt);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			return new String(cipher.doFinal(arr));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static SecretKey setKeyV2(String myKey) {
		MessageDigest sha = null;
		try {
			byte[] key = myKey.getBytes("UTF-8");
			return new SecretKeySpec(key, "AES");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> String responseMessage(Set<ConstraintViolation<T>> constraintViolations) {
		StringBuilder message = new StringBuilder();

		for (ConstraintViolation<T> violation : constraintViolations) {
			message.append(violation.getPropertyPath());
			message.append(" ");
			message.append(violation.getMessage());
			message.append(System.lineSeparator());

			return message.toString();
		}
		return message.toString();
	}

	public static boolean matches(String password, String encodedPassword, String secret) {
		if (encodedPassword == null || encodedPassword.length() == 0) {
			logger.warn("Empty encoded password");
			return false;
		}

		String pass = decrypt(encodedPassword, secret);

		return password.equals(pass);
	}
	
	public static String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	public static boolean isUserCodeMatch(String irequestContextUsercode, String reqUsercode) {
		if (irequestContextUsercode == null || irequestContextUsercode.length() == 0) {
			logger.warn("Empty irequestContextUsercode");
			return false;
		}

		if (reqUsercode == null || reqUsercode.length() == 0) {
			logger.warn("Empty reqUsercode");
			return false;
		}

		return irequestContextUsercode.equals(reqUsercode);
	}
}
