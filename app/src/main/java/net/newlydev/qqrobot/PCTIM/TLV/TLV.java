package net.newlydev.qqrobot.PCTIM.TLV;
import java.util.*;
import net.newlydev.qqrobot.PCTIM.Utils.*;

public class TLV
{
	private int _valueOffset;

	private TLV(int tag, int length, int valueOffset, byte[] data)
	{
		Tag = tag;
		Length = length;
		Data = data;
		Children = new ArrayList<TLV>();
	    Value = new byte[Length];
		_valueOffset = valueOffset;
		System.arraycopy(Data, _valueOffset, Value, 0, Length);
		
		HexValue = Util.byte2HexString(Value);
		HexData = Util.byte2HexString(Data);
		this.HexTag=Util.byte2HexString(Util.ToByte(tag));

		
	}

	/// <summary>
	///     The raw TLV data.
	/// </summary>
	public byte[] Data;

	/// <summary>
	///     The raw TLV data.
	/// </summary>
	public String HexData = "";

	/// <summary>
	///     The TLV tag.
	/// </summary>
	public int Tag ;

	/// <summary>
	///     The TLV tag.
	/// </summary>
	public String HexTag = "";
	/// <summary>
	///     The length of the TLV value.
	/// </summary>
	public int Length =0 ;

	/// <summary>
	///     The length of the TLV value.
	/// </summary>
	public String HexLength = Util.NumToHexString(Length, 4);

	/// <summary>
	///     The TLV value.
	/// </summary>
	
	public byte[] Value =null;
	
    
	

	/// <summary>
	///     The TLV value.
	/// </summary>
	public String HexValue = "";

	/// <summary>
	///     TLV children.
	/// </summary>
	public Collection<TLV> Children ;

	/// <summary>
	///     Parse TLV data.
	/// </summary>
	/// <param name="tlv">The hex TLV blob.</param>
	/// <returns>A collection of TLVs.</returns>
	public static Collection<TLV> ParseTlv(String tlv) throws Exception
	{
		if ((tlv).isEmpty())
		{
			throw new Exception("tlv");
		}

		return ParseTlv(Util.str_to_byte(tlv));
	}

	/// <summary>
	///     Parse TLV data.
	/// </summary>
	/// <param name="tlv">The byte array TLV blob.</param>
	/// <returns>A collection of TLVs.</returns>
	public static Collection<TLV> ParseTlv(byte[] tlv)
	{
		if (tlv == null || tlv.length == 0)
		{
			System.out.println("tlv parsfalse");
		}

		List<TLV> result = new ArrayList<TLV>();
		ParseTlv(tlv, result);

		return result;
	}

	private static void ParseTlv(byte[] rawTlv, Collection<TLV> result)
	{
	
		for (int i = 0, start = 0; i < rawTlv.length; start = i)
		{
		
			// parse Tag
			
			//i++
			i = i+ 2;

			int tag = Util.GetInt(rawTlv, start, i - start);

			//// parse Length
			//bool multiByteLength = (rawTlv[i] & 0x80) != 0;
			//int length = multiByteLength ? GetInt(rawTlv, i + 1, rawTlv[i] & 0x1F) : rawTlv[i];
			//i = multiByteLength ? i + (rawTlv[i] & 0x1F) + 1 : i + 1;
			i = i +2;
			
			start = start + 2;
			int length = Util.GetInt(rawTlv, start, i - start);
			i = i+ length ;
			byte[] rawData = new byte[i - start];
			
			System.arraycopy(rawTlv, start, rawData, 0, i - start);
			TLV tlv = new TLV(tag, length, rawData.length - length, rawData);
			result.add(tlv);

			
		}
	}


	
	
}
