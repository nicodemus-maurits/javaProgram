public class Measurement {

	public String createMeasurement() {
/*		char[] buffer = new char[1500];
		String Time = null;
		for (int i = 10; i<1500; i++)
		{
			buffer[i] =  (char) (i%128);
		}
		
		
		Time = String.valueOf(System.currentTimeMillis());
		
		//System.out.println(Time);

		String msg = new String(buffer, 0, 1500);
		//System.out.println(msg);
		
		String smsg = Time + msg;
		return smsg;
		*/
		
		String smsg = null;
		String Time = null;
		Time = String.valueOf(System.currentTimeMillis());
		for (int i = 0; i<50; i++)
		{
			/*if (smsg == null)
			{
				smsg = Time;
			}
			else
			{
				smsg =  smsg + Time;
			}*/
			smsg = Time;
		}
		System.out.println(smsg);
		return Time;
	}

}
