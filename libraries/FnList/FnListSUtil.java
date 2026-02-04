
// utility functions
public class FnListSUtil {
    public static<T>



    public static<T> 
        int length(FnList<T> xs) {
            int res = 0;
            while(true) {
                if (xs.nilq()) break;
                res += 1; xs = xs.tl();
            }
            return res;
        }

    public static<T>
        FnList<T> reverse(FnList<T> xs) {
            FnList<T> ys;
            ys = nil();
            while (!xs.nilq()) {
                ys = cons(xs.hd(), ys); xs = xs.tl();
            }
            return ys;
        }
}