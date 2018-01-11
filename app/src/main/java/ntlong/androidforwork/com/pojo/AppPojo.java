package ntlong.androidforwork.com.pojo;


import java.text.Collator;
import java.util.Comparator;

import ntlong.androidforwork.com.UserHandle;


public class AppPojo {
    public String id;
    public String appName;
    public String packageName;
    public String activityName;
    public UserHandle userHandle;

    public static class NameComparator implements Comparator<AppPojo> {
        private final Collator collator = Collator.getInstance();


        public final int compare(AppPojo a, AppPojo b) {
            int result = this.collator.compare(a.appName, b.appName);
            if(result == 0) {
                // Fall back to ID-based ordering if names match exactly
                result = this.collator.compare(a.id, b.id);
            }
            return result;
        }
    }
}
