import com.aye10032.utils.video.LiveInfo;
import org.junit.Test;

/**
 * @program: communismbot
 * @className: TeslLive
 * @Description:
 * @version: v1.0
 * @author: Aye10032
 * @date: 2023/1/24 下午 6:08
 */
public class TeslLive {

    @Test
    public void testLive(){
        LiveInfo liveInfo = new LiveInfo("1478953");
        System.out.println(liveInfo.Is_living());
    }

}
