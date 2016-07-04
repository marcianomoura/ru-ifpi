package br.com.ruifpi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.ruifpi.models.Funcionario;

public class CriptografaSenhaUtil {

	
	public static String criptografaSenha(Funcionario funcionario)	throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algoritmo.digest(funcionario.getSenha().getBytes("UTF-8"));
		StringBuilder builder = new StringBuilder();
		for (byte b : messageDigest) {
			builder.append(String.format("%02X", 0xFF & b));
		}
		String senhaCriptografada = builder.toString();
		return senhaCriptografada;
	}
}
