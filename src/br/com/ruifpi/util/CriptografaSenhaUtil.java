package br.com.ruifpi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CriptografaSenhaUtil {

	
	public static String criptografaSenha(String senha)	throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));
		StringBuilder builder = new StringBuilder();
		for (byte b : messageDigest) {
			builder.append(String.format("%02X", 0xFF & b));
		}
		String senhaCriptografada = builder.toString();
		return senhaCriptografada;
	}
}
