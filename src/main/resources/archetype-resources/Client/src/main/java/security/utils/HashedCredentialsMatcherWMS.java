#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security.utils;

import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.AbstractHash;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import ${package}.utils.Encodes;

@SuppressWarnings("deprecation")
public class HashedCredentialsMatcherWMS extends SimpleCredentialsMatcher {

	/**
	 * @since 1.1
	 */
	private String hashAlgorithm = "SHA-1";
	private int hashIterations = 1024;
	private boolean storedCredentialsHexEncoded = true;

	public HashedCredentialsMatcherWMS() {
	}

	public boolean isStoredCredentialsHexEncoded() {
		return storedCredentialsHexEncoded;
	}

	protected Object getCredentials(Object credentials) {

		byte[] storedBytes = toBytes(credentials);

		if (credentials instanceof String || credentials instanceof char[]) {
			// account.credentials were a char[] or String, so
			// we need to do text decoding first:
			if (isStoredCredentialsHexEncoded()) {
				storedBytes = Hex.decode(storedBytes);
			} else {
				storedBytes = Base64.decode(storedBytes);
			}
		}
		AbstractHash hash = newHashInstance();
		hash.setBytes(storedBytes);
		return hash;
	}

	public boolean doCredentialsMatch(String apassword, String password,
			String salt) {
		Object tokenHashedCredentials = hashProvidedCredentials(apassword,
				ByteSource.Util.bytes(Encodes.decodeHex(salt)));
		Object accountCredentials = getCredentials(password);
		return equals(tokenHashedCredentials, accountCredentials);
	}

	protected Hash hashProvidedCredentials(Object credentials, Object salt) {
		return new SimpleHash(hashAlgorithm, credentials, salt, hashIterations);
	}

	protected AbstractHash newHashInstance() {
		return new SimpleHash(hashAlgorithm);
	}

}
