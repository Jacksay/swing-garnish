
import com.jacksay.sgarnish.containers.JckApplicationFrame;

public class Test
{
	public static void main( String[] args )
	{
		new JckApplicationFrame() {
		@Override
		public void initializeConfiguration() {
		   msgClosing = false;
		}
	    }.setVisible(true);
	}
}
				
