package net.newlydev.qqrobot.PCTIM.Utils;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.security.*;

public class CrypterUtil
{

	public CrypterUtil()
	{
	}

	public static long getUnsignedInt(byte in[], int offset, int len)
	{
		long ret = 0L;
		int end = 0;
		if(len > 8)
			end = offset + 8;
		else
			end = offset + len;
		for(int i = offset; i < end; i++)
		{
			ret <<= 8;
			ret |= in[i] & 0xff;
		}

		return ret & 0xffffffffL | ret >>> 32;
	}

	private static boolean shouldFilterred(char c)
	{
		return (c >= '\0' || (CHARS[c] & 1) == 0) && ('\0' > c || c > '\0');
	}

	public static String filterUnprintableCharacter(String s)
	{
		sb.delete(0, sb.length());
		sb.append(s);
		for(; sb.length() > 0; sb.deleteCharAt(0))
		{
			char c = sb.charAt(0);
			if(!shouldFilterred(c))
				break;
		}

		for(; sb.length() > 0; sb.deleteCharAt(sb.length() - 1))
		{
			char c = sb.charAt(sb.length() - 1);
			if(!shouldFilterred(c))
				break;
		}

		int len = sb.length();
		for(int i = len - 1; i >= 0; i--)
		{
			char c = sb.charAt(i);
			if(shouldFilterred(c) && !Character.isSpaceChar(c))
				sb.deleteCharAt(i);
		}

		return sb.toString();
	}

	public static boolean isByteArrayEqual(byte b1[], byte b2[])
	{
		if(b1.length != b2.length)
			return false;
		for(int i = 0; i < b1.length; i++)
			if(b1[i] != b2[i])
				return false;

		return true;
	}

	public static boolean checkFileMD5(RandomAccessFile file, byte md5[])
	{
		return compareMD5(getFileMD5(file), md5);
	}

	public static boolean isIpZero(byte ip[])
	{
		for(int i = 0; i < ip.length; i++)
			if(ip[i] != 0)
				return false;

		return true;
	}

	public static boolean checkFileMD5(String filename, byte md5[])
	{
		return compareMD5(getFileMD5(filename), md5);
	}

	public static byte[] getFileMD5(String filename)
	{
		try
		{
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			byte md5[] = getFileMD5(file);
			file.close();
			return md5;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static byte[] getFileMD5(RandomAccessFile file)
	{
		try
		{
			file.seek(0L);
			byte buf[] = file.length() <= 0x98a000L ? new byte[(int)file.length()] : new byte[0x98a000];
			file.readFully(buf);
			MessageDigest md = MessageDigest.getInstance("MD5");

			
			return md.digest(buf);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static String getFileMD5String(String filename)
	{
		byte md5[] = getFileMD5(filename);
		if(md5 == null)
			return null;
		sb.delete(0, sb.length());
		for(int i = 0; i < md5.length; i++)
		{
			String s = Integer.toHexString(md5[i] & 0xff);
			if(s.length() < 2)
				sb.append('0').append(s);
			else
				sb.append(s);
		}

		return sb.toString().toUpperCase();
	}

	public static boolean compareMD5(byte m1[], byte m2[])
	{
		if(m1 == null || m2 == null)
			return true;
		for(int i = 0; i < 16; i++)
			if(m1[i] != m2[i])
				return false;

		return true;
	}

	public static byte[] getBytes(String s, String encoding)
	{
		try
		{
			return s.getBytes(encoding);
		}
		catch(UnsupportedEncodingException e)
		{
			return s.getBytes();
		}
	}

	public static byte[] getBytes(String s)
	{
		return getBytes(s, "GBK");
	}

	public static String getString(String s, String srcEncoding, String destEncoding)
	{
		try
		{
			return new String(s.getBytes(srcEncoding), destEncoding);
		}
		catch(UnsupportedEncodingException e)
		{
			return s;
		}
	}

	public static String getString(ByteBuffer buf, byte delimit)
	{
		baos.reset();
		byte b;
		for(; buf.hasRemaining(); baos.write(b))
		{
			b = buf.get();
			if(b == delimit)
				return getString(baos.toByteArray());
		}

		return getString(baos.toByteArray());
	}

	public static String getString(ByteBuffer buf)
	{
		baos.reset();
		for(; buf.hasRemaining(); baos.write(buf.get()));
		return getString(baos.toByteArray());
	}

	public static String getString(ByteBuffer buf, int len)
	{
		baos.reset();
		for(; buf.hasRemaining() && len-- > 0; baos.write(buf.get()));
		return getString(baos.toByteArray());
	}

	public static String getString(ByteBuffer buf, byte delimit, int maxLen)
	{
		baos.reset();
		byte b;
		for(; buf.hasRemaining() && maxLen-- > 0; baos.write(b))
		{
			b = buf.get();
			if(b == delimit)
				break;
		}

		for(; buf.hasRemaining() && maxLen-- > 0; buf.get());
		return getString(baos.toByteArray());
	}

	public static String getString(byte b[], String encoding)
	{
		try
		{
			return new String(b, encoding);
		}
		catch(UnsupportedEncodingException e)
		{
			return new String(b);
		}
	}

	public static String getString(byte b[])
	{
		return getString(b, "GBK");
	}

	public static String getString(byte b[], int offset, int len, String encoding)
	{
		try
		{
			return new String(b, offset, len, encoding);
		}
		catch(UnsupportedEncodingException e)
		{
			return new String(b, offset, len);
		}
	}

	public static String getString(byte b[], int offset, int len)
	{
		return getString(b, offset, len, "GBK");
	}

	public static int getInt(String s, int faultValue)
	{
		try
		{
			return Integer.parseInt(s);
		}
		catch(NumberFormatException e)
		{
			return faultValue;
		}
	}

	public static long getLong(String s, int radix, long faultValue)
	{
		try
		{
			return Long.parseLong(s, radix);
		}
		catch(NumberFormatException e)
		{
			return faultValue;
		}
	}

	public static int getInt(String s, int radix, int faultValue)
	{
		try
		{
			return Integer.parseInt(s, radix);
		}
		catch(NumberFormatException e)
		{
			return faultValue;
		}
	}

	public static boolean isInt(String s)
	{
		try
		{
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}

	public static char getChar(String s, int faultValue)
	{
		return (char)(getInt(s, faultValue) & 0xffff);
	}

	public static byte getByte(String s, int faultValue)
	{
		return (byte)(getInt(s, faultValue) & 0xff);
	}

	public static String getIpStringFromBytes(byte ip[])
	{
		sb.delete(0, sb.length());
		sb.append(ip[0] & 0xff);
		sb.append('.');
		sb.append(ip[1] & 0xff);
		sb.append('.');
		sb.append(ip[2] & 0xff);
		sb.append('.');
		sb.append(ip[3] & 0xff);
		return sb.toString();
	}

	public static byte[] getIpByteArrayFromString(String ip)
	{
		byte ret[] = new byte[4];
		StringTokenizer st = new StringTokenizer(ip, ".");
		try
		{
			ret[0] = (byte)(Integer.parseInt(st.nextToken()) & 0xff);
			ret[1] = (byte)(Integer.parseInt(st.nextToken()) & 0xff);
			ret[2] = (byte)(Integer.parseInt(st.nextToken()) & 0xff);
			ret[3] = (byte)(Integer.parseInt(st.nextToken()) & 0xff);
		}
		catch(Exception e)
		{

		}
		return ret;
	}

	public static boolean isIpEquals(byte ip1[], byte ip2[])
	{
		return ip1[0] == ip2[0] && ip1[1] == ip2[1] && ip1[2] == ip2[2] && ip1[3] == ip2[3];
	}

	public static String getCommandString(char cmd)
	{
		switch(cmd)
		{
			case 98: // 'b'
				return "QQ_CMD_REQUEST_LOGIN_TOKEN";

			case 1: // '\001'
				return "QQ.QQ_CMD_LOGOUT";

			case 2: // '\002'
				return "QQ.QQ_CMD_KEEP_ALIVE";

			case 4: // '\004'
				return "QQ.QQ_CMD_MODIFY_INFO";

			case 5: // '\005'
				return "QQ.QQ_CMD_SEARCH_USER";

			case 6: // '\006'
				return "QQ.QQ_CMD_GET_USER_INFO";

			case 92: // '\\'
				return "QQ_CMD_FRIEND_LEVEL_OP";

			case 167: 
				return "QQ_CMD_ADD_FRIEND_EX";

			case 10: // '\n'
				return "QQ.QQ_CMD_DELETE_FRIEND";

			case 11: // '\013'
				return "QQ.QQ_CMD_ADD_FRIEND_AUTH";

			case 13: // '\r'
				return "QQ.QQ_CMD_CHANGE_STATUS";

			case 18: // '\022'
				return "QQ.QQ_CMD_ACK_SYS_MSG";

			case 22: // '\026'
				return "QQ.QQ_CMD_SEND_IM";

			case 23: // '\027'
				return "QQ.QQ_CMD_RECV_IM";

			case 28: // '\034'
				return "QQ.QQ_CMD_REMOVE_SELF";

			case 34: // '"'
				return "QQ.QQ_CMD_LOGIN";

			case 38: // '&'
				return "QQ.QQ_CMD_GET_FRIEND_LIST";

			case 39: // '\''
				return "QQ.QQ_CMD_GET_FRIEND_ONLINE";

			case 48: // '0'
				return "QQ.QQ_CMD_CLUSTER_CMD";

			case 128: 
				return "QQ.QQ_CMD_RECV_MSG_SYS";

			case 129: 
				return "QQ.QQ_CMD_RECV_MSG_FRIEND_CHANGE_STATUS";

			case 29: // '\035'
				return "QQ_CMD_REQUEST_KEY";

			case 60: // '<'
				return "QQ_CMD_GROUP_NAME_OP";

			case 61: // '='
				return "QQ_CMD_UPLOAD_GROUP_FRIEND";

			case 88: // 'X'
				return "QQ_CMD_DOWNLOAD_GROUP_FRIEND";

			case 62: // '>'
				return "QQ_CMD_FRIEND_DATA_OP";

			case 97: // 'a'
				return "QQ_CMD_ADVANCED_SEARCH";

			case 95: // '_'
				return "QQ_CMD_GET_TEMP_CLUSTER_ONLINE_MEMBER";

			case 168: 
				return "QQ_CMD_AUTHORIZE";

			case 103: // 'g'
				return "QQ_CMD_SIGNATURE_OP";

			case 101: // 'e'
				return "QQ_CMD_USER_PROPERTY_OP";

			case 166: 
				return "QQ_CMD_WEATHER_OP";

			case 45: // '-'
				return "QQ_CMD_SEND_SMS";

			case 102: // 'f'
				return "QQ_CMD_TEMP_SESSION_OP";

			case 94: // '^'
				return "QQ_CMD_PRIVACY_DATA_OP";
		}
		return (new StringBuilder("UNKNOWN_TYPE ")).append(cmd).toString();
	}

	public static String getClusterCommandString(byte cmd)
	{
		switch(cmd)
		{
			case 1: // '\001'
				return "QQ_CLUSTER_CMD_CREATE_CLUSTER";

			case 2: // '\002'
				return "QQ_CLUSTER_CMD_MODIFY_MEMBER";

			case 3: // '\003'
				return "QQ_CLUSTER_CMD_MODIFY_CLUSTER_INFO";

			case 4: // '\004'
				return "QQ_CLUSTER_CMD_GET_CLUSTER_INFO";

			case 5: // '\005'
				return "QQ_CLUSTER_CMD_ACTIVATE_CLUSTER";

			case 6: // '\006'
				return "QQ_CLUSTER_CMD_SEARCH_CLUSTER";

			case 7: // '\007'
				return "QQ_CLUSTER_CMD_JOIN_CLUSTER";

			case 8: // '\b'
				return "QQ_CLUSTER_CMD_JOIN_CLUSTER_AUTH";

			case 9: // '\t'
				return "QQ_CLUSTER_CMD_EXIT_CLUSTER";

			case 11: // '\013'
				return "QQ_CLUSTER_CMD_GET_ONLINE_MEMBER";

			case 12: // '\f'
				return "QQ_CLUSTER_CMD_GET_MEMBER_INFO";

			case 51: // '3'
				return "QQ_CLUSTER_CMD_GET_TEMP_INFO";

			case 55: // '7'
				return "QQ_CLUSTER_CMD_ACTIVATE_TEMP";

			case 19: // '\023'
				return "QQ_CLUSTER_CMD_COMMIT_MEMBER_ORGANIZATION";

			case 17: // '\021'
				return "QQ_CLUSTER_CMD_COMMIT_MEMBER_ORGANIZATION";

			case 48: // '0'
				return "QQ_CLUSTER_CMD_CREATE_TEMP";

			case 50: // '2'
				return "QQ_CLUSTER_CMD_EXIT_TEMP";

			case 16: // '\020'
				return "QQ_CLUSTER_CMD_GET_CARD";

			case 15: // '\017'
				return "QQ_CLUSTER_CMD_GET_CARD_BATCH";

			case 25: // '\031'
				return "QQ_CLUSTER_CMD_GET_VERSION_ID";

			case 14: // '\016'
				return "QQ_CLUSTER_CMD_MODIFY_CARD";

			case 52: // '4'
				return "QQ_CLUSTER_CMD_MODIFY_TEMP_INFO";

			case 53: // '5'
				return "QQ_CLUSTER_CMD_SEND_TEMP_IM";

			case 10: // '\n'
			case 13: // '\r'
			case 18: // '\022'
			case 20: // '\024'
			case 21: // '\025'
			case 22: // '\026'
			case 23: // '\027'
			case 24: // '\030'
			case 26: // '\032'
			case 27: // '\033'
			case 28: // '\034'
			case 29: // '\035'
			case 30: // '\036'
			case 31: // '\037'
			case 32: // ' '
			case 33: // '!'
			case 34: // '"'
			case 35: // '#'
			case 36: // '$'
			case 37: // '%'
			case 38: // '&'
			case 39: // '\''
			case 40: // '('
			case 41: // ')'
			case 42: // '*'
			case 43: // '+'
			case 44: // ','
			case 45: // '-'
			case 46: // '.'
			case 47: // '/'
			case 49: // '1'
			case 54: // '6'
			default:
				return "Unknown QQ Cluster Command";
		}
	}

	public static String getFileCommandString(char command)
	{
		switch(command)
		{
			case 1: // '\001'
				return "QQ_FILE_CMD_HEART_BEAT";

			case 2: // '\002'
				return "QQ_FILE_CMD_HEART_BEAT_ACK";

			case 3: // '\003'
				return "QQ_FILE_CMD_TRANSFER_FINISHED";

			case 7: // '\007'
				return "QQ_FILE_CMD_FILE_OP";

			case 8: // '\b'
				return "QQ_FILE_CMD_FILE_OP_ACK";

			case 49: // '1'
				return "QQ_FILE_CMD_SENDER_SAY_HELLO";

			case 50: // '2'
				return "QQ_FILE_CMD_SENDER_SAY_HELLO_ACK";

			case 51: // '3'
				return "QQ_FILE_CMD_RECEIVER_SAY_HELLO";

			case 52: // '4'
				return "QQ_FILE_CMD_RECEIVER_SAY_HELLO_ACK";

			case 60: // '<'
				return "QQ_FILE_CMD_NOTIFY_IP_ACK";

			case 61: // '='
				return "QQ_FILE_CMD_PING";

			case 62: // '>'
				return "QQ_FILE_CMD_PONG";

			case 64: // '@'
				return "QQ_FILE_CMD_YES_I_AM_BEHIND_FIREWALL";
		}
		return (new StringBuilder("UNKNOWN TYPE ")).append(command).toString();
	}

	public static String getEncodingString(char encoding)
	{
		switch(encoding)
		{
			case 34306: 
				return "GBK";

			case 0: // '\0'
				return "ISO-8859-1";

			case 34307: 
				return "BIG5";
		}
		return "GBK";
	}

	public static String getNormalIMTypeString(char type)
	{
		switch(type)
		{
			case 11: // '\013'
				return "QQ_IM_NORMAL_TEXT";

			case 1: // '\001'
				return "QQ_IM_TCP_REQUEST";

			case 3: // '\003'
				return "QQ_IM_ACCEPT_TCP_REQUEST";

			case 5: // '\005'
				return "QQ_IM_REJECT_TCP_REQUEST";

			case 53: // '5'
				return "QQ_IM_UDP_REQUEST";

			case 55: // '7'
				return "QQ_IM_ACCEPT_UDP_REQUEST";

			case 57: // '9'
				return "QQ_IM_REJECT_UDP_REQUEST";

			case 59: // ';'
				return "QQ_IM_NOTIFY_IP";

			case 63: // '?'
				return "QQ_IM_ARE_YOU_BEHIND_FIREWALL";

			case 65: // 'A'
				return "QQ_IM_ARE_YOU_BEHIND_PROXY";

			case 66: // 'B'
				return "QQ_IM_YES_I_AM_BEHIND_PROXY";

			case 73: // 'I'
				return "QQ_IM_REQUEST_CANCELED";
		}
		return String.valueOf(type);
	}

	public static String getIMReplyType(byte type)
	{
		switch(type)
		{
			case 1: // '\001'
				return "QQ_IM_TEXT";

			case 2: // '\002'
				return "QQ_IM_AUTO_REPLY";
		}
		return "UNKNOWN";
	}

	public static int indexOf(byte buf[], int begin, byte b)
	{
		for(int i = begin; i < buf.length; i++)
			if(buf[i] == b)
				return i;

		return -1;
	}

	public static int indexOf(byte buf[], int begin, byte b[])
	{
		for(int i = begin; i < buf.length; i++)
		{
			for(int j = 0; j < b.length; j++)
				if(buf[i] == b[j])
					return i;

		}

		return -1;
	}

	public static Random random()
	{
		if(random == null)
			random = new Random();
		return random;
	}

	public static byte[] randomKey()
	{
		byte key[] = new byte[16];
		random().nextBytes(key);
		return key;
	}

	public static final int parseInt(byte content[], int offset)
	{
		return (content[offset++] & 0xff) << 24 | (content[offset++] & 0xff) << 16 | (content[offset++] & 0xff) << 8 | content[offset++] & 0xff;
	}

	public static final char parseChar(byte content[], int offset)
	{
		return (char)((content[offset++] & 0xff) << 8 | content[offset++] & 0xff);
	}

	public static final String getAuthActionString(byte b)
	{
		switch(b)
		{
			case 48: // '0'
				return "QQ_MY_AUTH_APPROVE";

			case 49: // '1'
				return "QQ_MY_AUTH_REJECT";

			case 50: // '2'
				return "QQ_MY_AUTH_REQUEST";
		}
		return "Unknown Action";
	}

	public static final String getAuthTypeString(byte b)
	{
		switch(b)
		{
			case 1: // '\001'
				return "QQ_AUTH_NEED";

			case 2: // '\002'
				return "QQ_AUTH_REJECT";

			case 0: // '\0'
				return "QQ_AUTH_NO";
		}
		return "Unknown Type";
	}

	public static final String getSearchTypeString(byte b)
	{
		switch(b)
		{
			case 1: // '\001'
				return "QQ_SEARCH_CLUSTER_BY_ID";

			case 2: // '\002'
				return "QQ_SEARCH_DEMO_CLUSTER";

			case 49: // '1'
				return "QQ_SEARCH_ALL";

			case 48: // '0'
				return "QQ_SEARCH_CUSTOM";
		}
		return "Unknown Search Type";
	}

	public static String convertByteToHexString(byte b[])
	{
		if(b == null)
			return "null";
		else
			return convertByteToHexString(b, 0, b.length);
	}

	public static String convertByteToHexString(byte b[], int offset, int len)
	{
		if(b == null)
			return "null";
		int end = offset + len;
		if(end > b.length)
			end = b.length;
		sb.delete(0, sb.length());
		for(int i = offset; i < end; i++)
			sb.append(hex[(b[i] & 0xf0) >>> 4]).append(hex[b[i] & 0xf]).append(' ');

		if(sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String convertByteToHexStringWithoutSpace(byte b[])
	{
		if(b == null)
			return "null";
		else
			return convertByteToHexStringWithoutSpace(b, 0, b.length);
	}

	public static String convertByteToHexStringWithoutSpace(byte b[], int offset, int len)
	{
		if(b == null)
			return "null";
		int end = offset + len;
		if(end > b.length)
			end = b.length;
		sb.delete(0, sb.length());
		for(int i = offset; i < end; i++)
			sb.append(hex[(b[i] & 0xf0) >>> 4]).append(hex[b[i] & 0xf]);

		return sb.toString();
	}

	public static byte[] convertHexStringToByte(String s)
	{
		try
		{
			s = s.trim();
			StringTokenizer st = new StringTokenizer(s, " ");
			byte ret[] = new byte[st.countTokens()];
			for(int i = 0; st.hasMoreTokens(); i++)
			{
				String byteString = st.nextToken();
				if(byteString.length() > 2)
					return null;
				ret[i] = (byte)(Integer.parseInt(byteString, 16) & 0xff);
			}

			return ret;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static byte[] convertHexStringToByteNoSpace(String s)
	{
		int len = s.length();
		byte ret[] = new byte[len >>> 1];
		for(int i = 0; i <= len - 2; i += 2)
			ret[i >>> 1] = (byte)(Integer.parseInt(s.substring(i, i + 2).trim(), 16) & 0xff);

		return ret;
	}

	public static String convertIpToString(byte ip[])
	{
		sb.delete(0, sb.length());
		for(int i = 0; i < ip.length; i++)
			sb.append(ip[i] & 0xff).append('.');

		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static int findByteOffset(byte ar[], byte b)
	{
		return findByteOffset(ar, b, 0);
	}

	public static int findByteOffset(byte ar[], byte b, int from)
	{
		for(int i = from; i < ar.length; i++)
			if(ar[i] == b)
				return i;

		return -1;
	}

	public static int findByteOffset(byte ar[], byte b, int from, int occurs)
	{
		int i = from;
		int j = 0;
		for(; i < ar.length; i++)
			if(ar[i] == b && ++j == occurs)
				return i;

		return -1;
	}

	public static byte[] convertCharToBytes(char c)
	{
		byte b[] = new byte[2];
		b[0] = (byte)((c & 0xff00) >>> 8);
		b[1] = (byte)(c & 0xff);
		return b;
	}

	public static int getIntFromBytes(byte b[], int offset, int len)
	{
		if(len > 4)
			len = 4;
		int ret = 0;
		int end = offset + len;
		for(int i = offset; i < end; i++)
		{
			ret |= b[i] & 0xff;
			if(i < end - 1)
				ret <<= 8;
		}

		return ret;
	}

	public static byte[] getSubBytes(byte b[], int offset, int len)
	{
		byte ret[] = new byte[len];
		System.arraycopy(b, offset, ret, 0, len);
		return ret;
	}

	public static String get05CommandString(char command)
	{
		switch(command)
		{
			case 33: // '!'
				return "QQ_05_CMD_REQUEST_AGENT";

			case 34: // '"'
				return "QQ_05_CMD_REQUEST_FACE";

			case 38: // '&'
				return "QQ_05_CMD_REQUEST_BEGIN";

			case 35: // '#'
				return "QQ_05_CMD_TRANSFER";

			case 36: // '$'
			case 37: // '%'
			default:
				return "UNKNOWN 05 FAMILY COMMAND";
		}
	}

	public static String getFTPCommandString(char command)
	{
		switch(command)
		{
			case 2: // '\002'
				return "QQ_03_CMD_GET_CUSTOM_HEAD_DATA";

			case 4: // '\004'
				return "QQ_03_CMD_GET_CUSTOM_HEAD_INFO";

			case 3: // '\003'
			default:
				return "UNKNOWN FTP FAMILY COMMAND";
		}
	}

	public static String getErrorString(int error)
	{
		switch(error)
		{
			case 1: // '\001'
				return "\u53D1\u9001\u8D85\u65F6";

			case 0: // '\0'
				return "\u8FDC\u7A0B\u8FDE\u63A5\u5DF2\u5173\u95ED";
		}
		return "";
	}

	public static String getProtocolString(int p)
	{
		switch(p)
		{
			case 4: // '\004'
				return "QQ_PROTOCOL_FAMILY_03";

			case 2: // '\002'
				return "QQ_PROTOCOL_FAMILY_05";

			case 1: // '\001'
				return "QQ_PROTOCOL_FAMILY_BASIC";

			case 8: // '\b'
				return "QQ_PROTOCOL_FAMILY_DISK";

			case 3: // '\003'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
			default:
				return "";
		}
	}

	private static Random random;
	private static ByteArrayOutputStream baos = new ByteArrayOutputStream();
	private static StringBuilder sb = new StringBuilder();
	private static char hex[] = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'A', 'B', 'C', 'D', 'E', 'F'
	};
	private static final byte CHARS[];
	public static final int MASK_VALID = 1;
	static Class class$0;

	static 
	{
		CHARS = new byte[0x10000];
		CHARS[9] = 35;
		CHARS[10] = 19;
		CHARS[13] = 19;
		CHARS[32] = 51;
		CHARS[33] = 49;
		CHARS[34] = 33;
		Arrays.fill(CHARS, 35, 38, (byte)49);
		CHARS[38] = 1;
		Arrays.fill(CHARS, 39, 45, (byte)49);
		Arrays.fill(CHARS, 45, 47, (byte)-71);
		CHARS[47] = 49;
		Arrays.fill(CHARS, 48, 58, (byte)-71);
		CHARS[58] = 61;
		CHARS[59] = 49;
		CHARS[60] = 1;
		CHARS[61] = 49;
		CHARS[62] = 33;
		Arrays.fill(CHARS, 63, 65, (byte)49);
		Arrays.fill(CHARS, 65, 91, (byte)-3);
		Arrays.fill(CHARS, 91, 93, (byte)33);
		CHARS[93] = 1;
		CHARS[94] = 33;
		CHARS[95] = -3;
		CHARS[96] = 33;
		Arrays.fill(CHARS, 97, 123, (byte)-3);
		Arrays.fill(CHARS, 123, 183, (byte)33);
		CHARS[183] = -87;
		Arrays.fill(CHARS, 184, 192, (byte)33);
		Arrays.fill(CHARS, 192, 215, (byte)-19);
		CHARS[215] = 33;
		Arrays.fill(CHARS, 216, 247, (byte)-19);
		CHARS[247] = 33;
		Arrays.fill(CHARS, 248, 306, (byte)-19);
		Arrays.fill(CHARS, 306, 308, (byte)33);
		Arrays.fill(CHARS, 308, 319, (byte)-19);
		Arrays.fill(CHARS, 319, 321, (byte)33);
		Arrays.fill(CHARS, 321, 329, (byte)-19);
		CHARS[329] = 33;
		Arrays.fill(CHARS, 330, 383, (byte)-19);
		CHARS[383] = 33;
		Arrays.fill(CHARS, 384, 452, (byte)-19);
		Arrays.fill(CHARS, 452, 461, (byte)33);
		Arrays.fill(CHARS, 461, 497, (byte)-19);
		Arrays.fill(CHARS, 497, 500, (byte)33);
		Arrays.fill(CHARS, 500, 502, (byte)-19);
		Arrays.fill(CHARS, 502, 506, (byte)33);
		Arrays.fill(CHARS, 506, 536, (byte)-19);
		Arrays.fill(CHARS, 536, 592, (byte)33);
		Arrays.fill(CHARS, 592, 681, (byte)-19);
		Arrays.fill(CHARS, 681, 699, (byte)33);
		Arrays.fill(CHARS, 699, 706, (byte)-19);
		Arrays.fill(CHARS, 706, 720, (byte)33);
		Arrays.fill(CHARS, 720, 722, (byte)-87);
		Arrays.fill(CHARS, 722, 768, (byte)33);
		Arrays.fill(CHARS, 768, 838, (byte)-87);
		Arrays.fill(CHARS, 838, 864, (byte)33);
		Arrays.fill(CHARS, 864, 866, (byte)-87);
		Arrays.fill(CHARS, 866, 902, (byte)33);
		CHARS[902] = -19;
		CHARS[903] = -87;
		Arrays.fill(CHARS, 904, 907, (byte)-19);
		CHARS[907] = 33;
		CHARS[908] = -19;
		CHARS[909] = 33;
		Arrays.fill(CHARS, 910, 930, (byte)-19);
		CHARS[930] = 33;
		Arrays.fill(CHARS, 931, 975, (byte)-19);
		CHARS[975] = 33;
		Arrays.fill(CHARS, 976, 983, (byte)-19);
		Arrays.fill(CHARS, 983, 986, (byte)33);
		CHARS[986] = -19;
		CHARS[987] = 33;
		CHARS[988] = -19;
		CHARS[989] = 33;
		CHARS[990] = -19;
		CHARS[991] = 33;
		CHARS[992] = -19;
		CHARS[993] = 33;
		Arrays.fill(CHARS, 994, 1012, (byte)-19);
		Arrays.fill(CHARS, 1012, 1025, (byte)33);
		Arrays.fill(CHARS, 1025, 1037, (byte)-19);
		CHARS[1037] = 33;
		Arrays.fill(CHARS, 1038, 1104, (byte)-19);
		CHARS[1104] = 33;
		Arrays.fill(CHARS, 1105, 1117, (byte)-19);
		CHARS[1117] = 33;
		Arrays.fill(CHARS, 1118, 1154, (byte)-19);
		CHARS[1154] = 33;
		Arrays.fill(CHARS, 1155, 1159, (byte)-87);
		Arrays.fill(CHARS, 1159, 1168, (byte)33);
		Arrays.fill(CHARS, 1168, 1221, (byte)-19);
		Arrays.fill(CHARS, 1221, 1223, (byte)33);
		Arrays.fill(CHARS, 1223, 1225, (byte)-19);
		Arrays.fill(CHARS, 1225, 1227, (byte)33);
		Arrays.fill(CHARS, 1227, 1229, (byte)-19);
		Arrays.fill(CHARS, 1229, 1232, (byte)33);
		Arrays.fill(CHARS, 1232, 1260, (byte)-19);
		Arrays.fill(CHARS, 1260, 1262, (byte)33);
		Arrays.fill(CHARS, 1262, 1270, (byte)-19);
		Arrays.fill(CHARS, 1270, 1272, (byte)33);
		Arrays.fill(CHARS, 1272, 1274, (byte)-19);
		Arrays.fill(CHARS, 1274, 1329, (byte)33);
		Arrays.fill(CHARS, 1329, 1367, (byte)-19);
		Arrays.fill(CHARS, 1367, 1369, (byte)33);
		CHARS[1369] = -19;
		Arrays.fill(CHARS, 1370, 1377, (byte)33);
		Arrays.fill(CHARS, 1377, 1415, (byte)-19);
		Arrays.fill(CHARS, 1415, 1425, (byte)33);
		Arrays.fill(CHARS, 1425, 1442, (byte)-87);
		CHARS[1442] = 33;
		Arrays.fill(CHARS, 1443, 1466, (byte)-87);
		CHARS[1466] = 33;
		Arrays.fill(CHARS, 1467, 1470, (byte)-87);
		CHARS[1470] = 33;
		CHARS[1471] = -87;
		CHARS[1472] = 33;
		Arrays.fill(CHARS, 1473, 1475, (byte)-87);
		CHARS[1475] = 33;
		CHARS[1476] = -87;
		Arrays.fill(CHARS, 1477, 1488, (byte)33);
		Arrays.fill(CHARS, 1488, 1515, (byte)-19);
		Arrays.fill(CHARS, 1515, 1520, (byte)33);
		Arrays.fill(CHARS, 1520, 1523, (byte)-19);
		Arrays.fill(CHARS, 1523, 1569, (byte)33);
		Arrays.fill(CHARS, 1569, 1595, (byte)-19);
		Arrays.fill(CHARS, 1595, 1600, (byte)33);
		CHARS[1600] = -87;
		Arrays.fill(CHARS, 1601, 1611, (byte)-19);
		Arrays.fill(CHARS, 1611, 1619, (byte)-87);
		Arrays.fill(CHARS, 1619, 1632, (byte)33);
		Arrays.fill(CHARS, 1632, 1642, (byte)-87);
		Arrays.fill(CHARS, 1642, 1648, (byte)33);
		CHARS[1648] = -87;
		Arrays.fill(CHARS, 1649, 1720, (byte)-19);
		Arrays.fill(CHARS, 1720, 1722, (byte)33);
		Arrays.fill(CHARS, 1722, 1727, (byte)-19);
		CHARS[1727] = 33;
		Arrays.fill(CHARS, 1728, 1743, (byte)-19);
		CHARS[1743] = 33;
		Arrays.fill(CHARS, 1744, 1748, (byte)-19);
		CHARS[1748] = 33;
		CHARS[1749] = -19;
		Arrays.fill(CHARS, 1750, 1765, (byte)-87);
		Arrays.fill(CHARS, 1765, 1767, (byte)-19);
		Arrays.fill(CHARS, 1767, 1769, (byte)-87);
		CHARS[1769] = 33;
		Arrays.fill(CHARS, 1770, 1774, (byte)-87);
		Arrays.fill(CHARS, 1774, 1776, (byte)33);
		Arrays.fill(CHARS, 1776, 1786, (byte)-87);
		Arrays.fill(CHARS, 1786, 2305, (byte)33);
		Arrays.fill(CHARS, 2305, 2308, (byte)-87);
		CHARS[2308] = 33;
		Arrays.fill(CHARS, 2309, 2362, (byte)-19);
		Arrays.fill(CHARS, 2362, 2364, (byte)33);
		CHARS[2364] = -87;
		CHARS[2365] = -19;
		Arrays.fill(CHARS, 2366, 2382, (byte)-87);
		Arrays.fill(CHARS, 2382, 2385, (byte)33);
		Arrays.fill(CHARS, 2385, 2389, (byte)-87);
		Arrays.fill(CHARS, 2389, 2392, (byte)33);
		Arrays.fill(CHARS, 2392, 2402, (byte)-19);
		Arrays.fill(CHARS, 2402, 2404, (byte)-87);
		Arrays.fill(CHARS, 2404, 2406, (byte)33);
		Arrays.fill(CHARS, 2406, 2416, (byte)-87);
		Arrays.fill(CHARS, 2416, 2433, (byte)33);
		Arrays.fill(CHARS, 2433, 2436, (byte)-87);
		CHARS[2436] = 33;
		Arrays.fill(CHARS, 2437, 2445, (byte)-19);
		Arrays.fill(CHARS, 2445, 2447, (byte)33);
		Arrays.fill(CHARS, 2447, 2449, (byte)-19);
		Arrays.fill(CHARS, 2449, 2451, (byte)33);
		Arrays.fill(CHARS, 2451, 2473, (byte)-19);
		CHARS[2473] = 33;
		Arrays.fill(CHARS, 2474, 2481, (byte)-19);
		CHARS[2481] = 33;
		CHARS[2482] = -19;
		Arrays.fill(CHARS, 2483, 2486, (byte)33);
		Arrays.fill(CHARS, 2486, 2490, (byte)-19);
		Arrays.fill(CHARS, 2490, 2492, (byte)33);
		CHARS[2492] = -87;
		CHARS[2493] = 33;
		Arrays.fill(CHARS, 2494, 2501, (byte)-87);
		Arrays.fill(CHARS, 2501, 2503, (byte)33);
		Arrays.fill(CHARS, 2503, 2505, (byte)-87);
		Arrays.fill(CHARS, 2505, 2507, (byte)33);
		Arrays.fill(CHARS, 2507, 2510, (byte)-87);
		Arrays.fill(CHARS, 2510, 2519, (byte)33);
		CHARS[2519] = -87;
		Arrays.fill(CHARS, 2520, 2524, (byte)33);
		Arrays.fill(CHARS, 2524, 2526, (byte)-19);
		CHARS[2526] = 33;
		Arrays.fill(CHARS, 2527, 2530, (byte)-19);
		Arrays.fill(CHARS, 2530, 2532, (byte)-87);
		Arrays.fill(CHARS, 2532, 2534, (byte)33);
		Arrays.fill(CHARS, 2534, 2544, (byte)-87);
		Arrays.fill(CHARS, 2544, 2546, (byte)-19);
		Arrays.fill(CHARS, 2546, 2562, (byte)33);
		CHARS[2562] = -87;
		Arrays.fill(CHARS, 2563, 2565, (byte)33);
		Arrays.fill(CHARS, 2565, 2571, (byte)-19);
		Arrays.fill(CHARS, 2571, 2575, (byte)33);
		Arrays.fill(CHARS, 2575, 2577, (byte)-19);
		Arrays.fill(CHARS, 2577, 2579, (byte)33);
		Arrays.fill(CHARS, 2579, 2601, (byte)-19);
		CHARS[2601] = 33;
		Arrays.fill(CHARS, 2602, 2609, (byte)-19);
		CHARS[2609] = 33;
		Arrays.fill(CHARS, 2610, 2612, (byte)-19);
		CHARS[2612] = 33;
		Arrays.fill(CHARS, 2613, 2615, (byte)-19);
		CHARS[2615] = 33;
		Arrays.fill(CHARS, 2616, 2618, (byte)-19);
		Arrays.fill(CHARS, 2618, 2620, (byte)33);
		CHARS[2620] = -87;
		CHARS[2621] = 33;
		Arrays.fill(CHARS, 2622, 2627, (byte)-87);
		Arrays.fill(CHARS, 2627, 2631, (byte)33);
		Arrays.fill(CHARS, 2631, 2633, (byte)-87);
		Arrays.fill(CHARS, 2633, 2635, (byte)33);
		Arrays.fill(CHARS, 2635, 2638, (byte)-87);
		Arrays.fill(CHARS, 2638, 2649, (byte)33);
		Arrays.fill(CHARS, 2649, 2653, (byte)-19);
		CHARS[2653] = 33;
		CHARS[2654] = -19;
		Arrays.fill(CHARS, 2655, 2662, (byte)33);
		Arrays.fill(CHARS, 2662, 2674, (byte)-87);
		Arrays.fill(CHARS, 2674, 2677, (byte)-19);
		Arrays.fill(CHARS, 2677, 2689, (byte)33);
		Arrays.fill(CHARS, 2689, 2692, (byte)-87);
		CHARS[2692] = 33;
		Arrays.fill(CHARS, 2693, 2700, (byte)-19);
		CHARS[2700] = 33;
		CHARS[2701] = -19;
		CHARS[2702] = 33;
		Arrays.fill(CHARS, 2703, 2706, (byte)-19);
		CHARS[2706] = 33;
		Arrays.fill(CHARS, 2707, 2729, (byte)-19);
		CHARS[2729] = 33;
		Arrays.fill(CHARS, 2730, 2737, (byte)-19);
		CHARS[2737] = 33;
		Arrays.fill(CHARS, 2738, 2740, (byte)-19);
		CHARS[2740] = 33;
		Arrays.fill(CHARS, 2741, 2746, (byte)-19);
		Arrays.fill(CHARS, 2746, 2748, (byte)33);
		CHARS[2748] = -87;
		CHARS[2749] = -19;
		Arrays.fill(CHARS, 2750, 2758, (byte)-87);
		CHARS[2758] = 33;
		Arrays.fill(CHARS, 2759, 2762, (byte)-87);
		CHARS[2762] = 33;
		Arrays.fill(CHARS, 2763, 2766, (byte)-87);
		Arrays.fill(CHARS, 2766, 2784, (byte)33);
		CHARS[2784] = -19;
		Arrays.fill(CHARS, 2785, 2790, (byte)33);
		Arrays.fill(CHARS, 2790, 2800, (byte)-87);
		Arrays.fill(CHARS, 2800, 2817, (byte)33);
		Arrays.fill(CHARS, 2817, 2820, (byte)-87);
		CHARS[2820] = 33;
		Arrays.fill(CHARS, 2821, 2829, (byte)-19);
		Arrays.fill(CHARS, 2829, 2831, (byte)33);
		Arrays.fill(CHARS, 2831, 2833, (byte)-19);
		Arrays.fill(CHARS, 2833, 2835, (byte)33);
		Arrays.fill(CHARS, 2835, 2857, (byte)-19);
		CHARS[2857] = 33;
		Arrays.fill(CHARS, 2858, 2865, (byte)-19);
		CHARS[2865] = 33;
		Arrays.fill(CHARS, 2866, 2868, (byte)-19);
		Arrays.fill(CHARS, 2868, 2870, (byte)33);
		Arrays.fill(CHARS, 2870, 2874, (byte)-19);
		Arrays.fill(CHARS, 2874, 2876, (byte)33);
		CHARS[2876] = -87;
		CHARS[2877] = -19;
		Arrays.fill(CHARS, 2878, 2884, (byte)-87);
		Arrays.fill(CHARS, 2884, 2887, (byte)33);
		Arrays.fill(CHARS, 2887, 2889, (byte)-87);
		Arrays.fill(CHARS, 2889, 2891, (byte)33);
		Arrays.fill(CHARS, 2891, 2894, (byte)-87);
		Arrays.fill(CHARS, 2894, 2902, (byte)33);
		Arrays.fill(CHARS, 2902, 2904, (byte)-87);
		Arrays.fill(CHARS, 2904, 2908, (byte)33);
		Arrays.fill(CHARS, 2908, 2910, (byte)-19);
		CHARS[2910] = 33;
		Arrays.fill(CHARS, 2911, 2914, (byte)-19);
		Arrays.fill(CHARS, 2914, 2918, (byte)33);
		Arrays.fill(CHARS, 2918, 2928, (byte)-87);
		Arrays.fill(CHARS, 2928, 2946, (byte)33);
		Arrays.fill(CHARS, 2946, 2948, (byte)-87);
		CHARS[2948] = 33;
		Arrays.fill(CHARS, 2949, 2955, (byte)-19);
		Arrays.fill(CHARS, 2955, 2958, (byte)33);
		Arrays.fill(CHARS, 2958, 2961, (byte)-19);
		CHARS[2961] = 33;
		Arrays.fill(CHARS, 2962, 2966, (byte)-19);
		Arrays.fill(CHARS, 2966, 2969, (byte)33);
		Arrays.fill(CHARS, 2969, 2971, (byte)-19);
		CHARS[2971] = 33;
		CHARS[2972] = -19;
		CHARS[2973] = 33;
		Arrays.fill(CHARS, 2974, 2976, (byte)-19);
		Arrays.fill(CHARS, 2976, 2979, (byte)33);
		Arrays.fill(CHARS, 2979, 2981, (byte)-19);
		Arrays.fill(CHARS, 2981, 2984, (byte)33);
		Arrays.fill(CHARS, 2984, 2987, (byte)-19);
		Arrays.fill(CHARS, 2987, 2990, (byte)33);
		Arrays.fill(CHARS, 2990, 2998, (byte)-19);
		CHARS[2998] = 33;
		Arrays.fill(CHARS, 2999, 3002, (byte)-19);
		Arrays.fill(CHARS, 3002, 3006, (byte)33);
		Arrays.fill(CHARS, 3006, 3011, (byte)-87);
		Arrays.fill(CHARS, 3011, 3014, (byte)33);
		Arrays.fill(CHARS, 3014, 3017, (byte)-87);
		CHARS[3017] = 33;
		Arrays.fill(CHARS, 3018, 3022, (byte)-87);
		Arrays.fill(CHARS, 3022, 3031, (byte)33);
		CHARS[3031] = -87;
		Arrays.fill(CHARS, 3032, 3047, (byte)33);
		Arrays.fill(CHARS, 3047, 3056, (byte)-87);
		Arrays.fill(CHARS, 3056, 3073, (byte)33);
		Arrays.fill(CHARS, 3073, 3076, (byte)-87);
		CHARS[3076] = 33;
		Arrays.fill(CHARS, 3077, 3085, (byte)-19);
		CHARS[3085] = 33;
		Arrays.fill(CHARS, 3086, 3089, (byte)-19);
		CHARS[3089] = 33;
		Arrays.fill(CHARS, 3090, 3113, (byte)-19);
		CHARS[3113] = 33;
		Arrays.fill(CHARS, 3114, 3124, (byte)-19);
		CHARS[3124] = 33;
		Arrays.fill(CHARS, 3125, 3130, (byte)-19);
		Arrays.fill(CHARS, 3130, 3134, (byte)33);
		Arrays.fill(CHARS, 3134, 3141, (byte)-87);
		CHARS[3141] = 33;
		Arrays.fill(CHARS, 3142, 3145, (byte)-87);
		CHARS[3145] = 33;
		Arrays.fill(CHARS, 3146, 3150, (byte)-87);
		Arrays.fill(CHARS, 3150, 3157, (byte)33);
		Arrays.fill(CHARS, 3157, 3159, (byte)-87);
		Arrays.fill(CHARS, 3159, 3168, (byte)33);
		Arrays.fill(CHARS, 3168, 3170, (byte)-19);
		Arrays.fill(CHARS, 3170, 3174, (byte)33);
		Arrays.fill(CHARS, 3174, 3184, (byte)-87);
		Arrays.fill(CHARS, 3184, 3202, (byte)33);
		Arrays.fill(CHARS, 3202, 3204, (byte)-87);
		CHARS[3204] = 33;
		Arrays.fill(CHARS, 3205, 3213, (byte)-19);
		CHARS[3213] = 33;
		Arrays.fill(CHARS, 3214, 3217, (byte)-19);
		CHARS[3217] = 33;
		Arrays.fill(CHARS, 3218, 3241, (byte)-19);
		CHARS[3241] = 33;
		Arrays.fill(CHARS, 3242, 3252, (byte)-19);
		CHARS[3252] = 33;
		Arrays.fill(CHARS, 3253, 3258, (byte)-19);
		Arrays.fill(CHARS, 3258, 3262, (byte)33);
		Arrays.fill(CHARS, 3262, 3269, (byte)-87);
		CHARS[3269] = 33;
		Arrays.fill(CHARS, 3270, 3273, (byte)-87);
		CHARS[3273] = 33;
		Arrays.fill(CHARS, 3274, 3278, (byte)-87);
		Arrays.fill(CHARS, 3278, 3285, (byte)33);
		Arrays.fill(CHARS, 3285, 3287, (byte)-87);
		Arrays.fill(CHARS, 3287, 3294, (byte)33);
		CHARS[3294] = -19;
		CHARS[3295] = 33;
		Arrays.fill(CHARS, 3296, 3298, (byte)-19);
		Arrays.fill(CHARS, 3298, 3302, (byte)33);
		Arrays.fill(CHARS, 3302, 3312, (byte)-87);
		Arrays.fill(CHARS, 3312, 3330, (byte)33);
		Arrays.fill(CHARS, 3330, 3332, (byte)-87);
		CHARS[3332] = 33;
		Arrays.fill(CHARS, 3333, 3341, (byte)-19);
		CHARS[3341] = 33;
		Arrays.fill(CHARS, 3342, 3345, (byte)-19);
		CHARS[3345] = 33;
		Arrays.fill(CHARS, 3346, 3369, (byte)-19);
		CHARS[3369] = 33;
		Arrays.fill(CHARS, 3370, 3386, (byte)-19);
		Arrays.fill(CHARS, 3386, 3390, (byte)33);
		Arrays.fill(CHARS, 3390, 3396, (byte)-87);
		Arrays.fill(CHARS, 3396, 3398, (byte)33);
		Arrays.fill(CHARS, 3398, 3401, (byte)-87);
		CHARS[3401] = 33;
		Arrays.fill(CHARS, 3402, 3406, (byte)-87);
		Arrays.fill(CHARS, 3406, 3415, (byte)33);
		CHARS[3415] = -87;
		Arrays.fill(CHARS, 3416, 3424, (byte)33);
		Arrays.fill(CHARS, 3424, 3426, (byte)-19);
		Arrays.fill(CHARS, 3426, 3430, (byte)33);
		Arrays.fill(CHARS, 3430, 3440, (byte)-87);
		Arrays.fill(CHARS, 3440, 3585, (byte)33);
		Arrays.fill(CHARS, 3585, 3631, (byte)-19);
		CHARS[3631] = 33;
		CHARS[3632] = -19;
		CHARS[3633] = -87;
		Arrays.fill(CHARS, 3634, 3636, (byte)-19);
		Arrays.fill(CHARS, 3636, 3643, (byte)-87);
		Arrays.fill(CHARS, 3643, 3648, (byte)33);
		Arrays.fill(CHARS, 3648, 3654, (byte)-19);
		Arrays.fill(CHARS, 3654, 3663, (byte)-87);
		CHARS[3663] = 33;
		Arrays.fill(CHARS, 3664, 3674, (byte)-87);
		Arrays.fill(CHARS, 3674, 3713, (byte)33);
		Arrays.fill(CHARS, 3713, 3715, (byte)-19);
		CHARS[3715] = 33;
		CHARS[3716] = -19;
		Arrays.fill(CHARS, 3717, 3719, (byte)33);
		Arrays.fill(CHARS, 3719, 3721, (byte)-19);
		CHARS[3721] = 33;
		CHARS[3722] = -19;
		Arrays.fill(CHARS, 3723, 3725, (byte)33);
		CHARS[3725] = -19;
		Arrays.fill(CHARS, 3726, 3732, (byte)33);
		Arrays.fill(CHARS, 3732, 3736, (byte)-19);
		CHARS[3736] = 33;
		Arrays.fill(CHARS, 3737, 3744, (byte)-19);
		CHARS[3744] = 33;
		Arrays.fill(CHARS, 3745, 3748, (byte)-19);
		CHARS[3748] = 33;
		CHARS[3749] = -19;
		CHARS[3750] = 33;
		CHARS[3751] = -19;
		Arrays.fill(CHARS, 3752, 3754, (byte)33);
		Arrays.fill(CHARS, 3754, 3756, (byte)-19);
		CHARS[3756] = 33;
		Arrays.fill(CHARS, 3757, 3759, (byte)-19);
		CHARS[3759] = 33;
		CHARS[3760] = -19;
		CHARS[3761] = -87;
		Arrays.fill(CHARS, 3762, 3764, (byte)-19);
		Arrays.fill(CHARS, 3764, 3770, (byte)-87);
		CHARS[3770] = 33;
		Arrays.fill(CHARS, 3771, 3773, (byte)-87);
		CHARS[3773] = -19;
		Arrays.fill(CHARS, 3774, 3776, (byte)33);
		Arrays.fill(CHARS, 3776, 3781, (byte)-19);
		CHARS[3781] = 33;
		CHARS[3782] = -87;
		CHARS[3783] = 33;
		Arrays.fill(CHARS, 3784, 3790, (byte)-87);
		Arrays.fill(CHARS, 3790, 3792, (byte)33);
		Arrays.fill(CHARS, 3792, 3802, (byte)-87);
		Arrays.fill(CHARS, 3802, 3864, (byte)33);
		Arrays.fill(CHARS, 3864, 3866, (byte)-87);
		Arrays.fill(CHARS, 3866, 3872, (byte)33);
		Arrays.fill(CHARS, 3872, 3882, (byte)-87);
		Arrays.fill(CHARS, 3882, 3893, (byte)33);
		CHARS[3893] = -87;
		CHARS[3894] = 33;
		CHARS[3895] = -87;
		CHARS[3896] = 33;
		CHARS[3897] = -87;
		Arrays.fill(CHARS, 3898, 3902, (byte)33);
		Arrays.fill(CHARS, 3902, 3904, (byte)-87);
		Arrays.fill(CHARS, 3904, 3912, (byte)-19);
		CHARS[3912] = 33;
		Arrays.fill(CHARS, 3913, 3946, (byte)-19);
		Arrays.fill(CHARS, 3946, 3953, (byte)33);
		Arrays.fill(CHARS, 3953, 3973, (byte)-87);
		CHARS[3973] = 33;
		Arrays.fill(CHARS, 3974, 3980, (byte)-87);
		Arrays.fill(CHARS, 3980, 3984, (byte)33);
		Arrays.fill(CHARS, 3984, 3990, (byte)-87);
		CHARS[3990] = 33;
		CHARS[3991] = -87;
		CHARS[3992] = 33;
		Arrays.fill(CHARS, 3993, 4014, (byte)-87);
		Arrays.fill(CHARS, 4014, 4017, (byte)33);
		Arrays.fill(CHARS, 4017, 4024, (byte)-87);
		CHARS[4024] = 33;
		CHARS[4025] = -87;
		Arrays.fill(CHARS, 4026, 4256, (byte)33);
		Arrays.fill(CHARS, 4256, 4294, (byte)-19);
		Arrays.fill(CHARS, 4294, 4304, (byte)33);
		Arrays.fill(CHARS, 4304, 4343, (byte)-19);
		Arrays.fill(CHARS, 4343, 4352, (byte)33);
		CHARS[4352] = -19;
		CHARS[4353] = 33;
		Arrays.fill(CHARS, 4354, 4356, (byte)-19);
		CHARS[4356] = 33;
		Arrays.fill(CHARS, 4357, 4360, (byte)-19);
		CHARS[4360] = 33;
		CHARS[4361] = -19;
		CHARS[4362] = 33;
		Arrays.fill(CHARS, 4363, 4365, (byte)-19);
		CHARS[4365] = 33;
		Arrays.fill(CHARS, 4366, 4371, (byte)-19);
		Arrays.fill(CHARS, 4371, 4412, (byte)33);
		CHARS[4412] = -19;
		CHARS[4413] = 33;
		CHARS[4414] = -19;
		CHARS[4415] = 33;
		CHARS[4416] = -19;
		Arrays.fill(CHARS, 4417, 4428, (byte)33);
		CHARS[4428] = -19;
		CHARS[4429] = 33;
		CHARS[4430] = -19;
		CHARS[4431] = 33;
		CHARS[4432] = -19;
		Arrays.fill(CHARS, 4433, 4436, (byte)33);
		Arrays.fill(CHARS, 4436, 4438, (byte)-19);
		Arrays.fill(CHARS, 4438, 4441, (byte)33);
		CHARS[4441] = -19;
		Arrays.fill(CHARS, 4442, 4447, (byte)33);
		Arrays.fill(CHARS, 4447, 4450, (byte)-19);
		CHARS[4450] = 33;
		CHARS[4451] = -19;
		CHARS[4452] = 33;
		CHARS[4453] = -19;
		CHARS[4454] = 33;
		CHARS[4455] = -19;
		CHARS[4456] = 33;
		CHARS[4457] = -19;
		Arrays.fill(CHARS, 4458, 4461, (byte)33);
		Arrays.fill(CHARS, 4461, 4463, (byte)-19);
		Arrays.fill(CHARS, 4463, 4466, (byte)33);
		Arrays.fill(CHARS, 4466, 4468, (byte)-19);
		CHARS[4468] = 33;
		CHARS[4469] = -19;
		Arrays.fill(CHARS, 4470, 4510, (byte)33);
		CHARS[4510] = -19;
		Arrays.fill(CHARS, 4511, 4520, (byte)33);
		CHARS[4520] = -19;
		Arrays.fill(CHARS, 4521, 4523, (byte)33);
		CHARS[4523] = -19;
		Arrays.fill(CHARS, 4524, 4526, (byte)33);
		Arrays.fill(CHARS, 4526, 4528, (byte)-19);
		Arrays.fill(CHARS, 4528, 4535, (byte)33);
		Arrays.fill(CHARS, 4535, 4537, (byte)-19);
		CHARS[4537] = 33;
		CHARS[4538] = -19;
		CHARS[4539] = 33;
		Arrays.fill(CHARS, 4540, 4547, (byte)-19);
		Arrays.fill(CHARS, 4547, 4587, (byte)33);
		CHARS[4587] = -19;
		Arrays.fill(CHARS, 4588, 4592, (byte)33);
		CHARS[4592] = -19;
		Arrays.fill(CHARS, 4593, 4601, (byte)33);
		CHARS[4601] = -19;
		Arrays.fill(CHARS, 4602, 7680, (byte)33);
		Arrays.fill(CHARS, 7680, 7836, (byte)-19);
		Arrays.fill(CHARS, 7836, 7840, (byte)33);
		Arrays.fill(CHARS, 7840, 7930, (byte)-19);
		Arrays.fill(CHARS, 7930, 7936, (byte)33);
		Arrays.fill(CHARS, 7936, 7958, (byte)-19);
		Arrays.fill(CHARS, 7958, 7960, (byte)33);
		Arrays.fill(CHARS, 7960, 7966, (byte)-19);
		Arrays.fill(CHARS, 7966, 7968, (byte)33);
		Arrays.fill(CHARS, 7968, 8006, (byte)-19);
		Arrays.fill(CHARS, 8006, 8008, (byte)33);
		Arrays.fill(CHARS, 8008, 8014, (byte)-19);
		Arrays.fill(CHARS, 8014, 8016, (byte)33);
		Arrays.fill(CHARS, 8016, 8024, (byte)-19);
		CHARS[8024] = 33;
		CHARS[8025] = -19;
		CHARS[8026] = 33;
		CHARS[8027] = -19;
		CHARS[8028] = 33;
		CHARS[8029] = -19;
		CHARS[8030] = 33;
		Arrays.fill(CHARS, 8031, 8062, (byte)-19);
		Arrays.fill(CHARS, 8062, 8064, (byte)33);
		Arrays.fill(CHARS, 8064, 8117, (byte)-19);
		CHARS[8117] = 33;
		Arrays.fill(CHARS, 8118, 8125, (byte)-19);
		CHARS[8125] = 33;
		CHARS[8126] = -19;
		Arrays.fill(CHARS, 8127, 8130, (byte)33);
		Arrays.fill(CHARS, 8130, 8133, (byte)-19);
		CHARS[8133] = 33;
		Arrays.fill(CHARS, 8134, 8141, (byte)-19);
		Arrays.fill(CHARS, 8141, 8144, (byte)33);
		Arrays.fill(CHARS, 8144, 8148, (byte)-19);
		Arrays.fill(CHARS, 8148, 8150, (byte)33);
		Arrays.fill(CHARS, 8150, 8156, (byte)-19);
		Arrays.fill(CHARS, 8156, 8160, (byte)33);
		Arrays.fill(CHARS, 8160, 8173, (byte)-19);
		Arrays.fill(CHARS, 8173, 8178, (byte)33);
		Arrays.fill(CHARS, 8178, 8181, (byte)-19);
		CHARS[8181] = 33;
		Arrays.fill(CHARS, 8182, 8189, (byte)-19);
		Arrays.fill(CHARS, 8189, 8400, (byte)33);
		Arrays.fill(CHARS, 8400, 8413, (byte)-87);
		Arrays.fill(CHARS, 8413, 8417, (byte)33);
		CHARS[8417] = -87;
		Arrays.fill(CHARS, 8418, 8486, (byte)33);
		CHARS[8486] = -19;
		Arrays.fill(CHARS, 8487, 8490, (byte)33);
		Arrays.fill(CHARS, 8490, 8492, (byte)-19);
		Arrays.fill(CHARS, 8492, 8494, (byte)33);
		CHARS[8494] = -19;
		Arrays.fill(CHARS, 8495, 8576, (byte)33);
		Arrays.fill(CHARS, 8576, 8579, (byte)-19);
		Arrays.fill(CHARS, 8579, 12293, (byte)33);
		CHARS[12293] = -87;
		CHARS[12294] = 33;
		CHARS[12295] = -19;
		Arrays.fill(CHARS, 12296, 12321, (byte)33);
		Arrays.fill(CHARS, 12321, 12330, (byte)-19);
		Arrays.fill(CHARS, 12330, 12336, (byte)-87);
		CHARS[12336] = 33;
		Arrays.fill(CHARS, 12337, 12342, (byte)-87);
		Arrays.fill(CHARS, 12342, 12353, (byte)33);
		Arrays.fill(CHARS, 12353, 12437, (byte)-19);
		Arrays.fill(CHARS, 12437, 12441, (byte)33);
		Arrays.fill(CHARS, 12441, 12443, (byte)-87);
		Arrays.fill(CHARS, 12443, 12445, (byte)33);
		Arrays.fill(CHARS, 12445, 12447, (byte)-87);
		Arrays.fill(CHARS, 12447, 12449, (byte)33);
		Arrays.fill(CHARS, 12449, 12539, (byte)-19);
		CHARS[12539] = 33;
		Arrays.fill(CHARS, 12540, 12543, (byte)-87);
		Arrays.fill(CHARS, 12543, 12549, (byte)33);
		Arrays.fill(CHARS, 12549, 12589, (byte)-19);
		Arrays.fill(CHARS, 12589, 19968, (byte)33);
		Arrays.fill(CHARS, 19968, 40870, (byte)-19);
		Arrays.fill(CHARS, 40870, 44032, (byte)33);
		Arrays.fill(CHARS, 44032, 55204, (byte)-19);
		Arrays.fill(CHARS, 55204, 55296, (byte)33);
		Arrays.fill(CHARS, 57344, 65534, (byte)33);
	}
}

