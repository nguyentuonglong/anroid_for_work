package ntlong.androidforwork.com

import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.os.Build
import android.os.Bundle
import android.os.UserManager
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import ntlong.androidforwork.com.adapter.AppAdapter
import ntlong.androidforwork.com.pojo.AppPojo
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private var pojoScheme = "(none)://"

    private var apps = ArrayList<AppPojo>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "Enter LOLLIPOP")

            val manager = this.getSystemService(Context.USER_SERVICE) as UserManager
            val launcher = this.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

            // Handle multi-profile support introduced in Android 5 (#542)
            for (profile in manager.userProfiles) {
                val user = UserHandle(manager.getSerialNumberForUser(profile), profile)
                for (activityInfo in launcher.getActivityList(null, profile)) {
                    val appInfo = activityInfo.applicationInfo

                    val app = AppPojo()

                    val fullPackageName = user.addUserSuffixToString(appInfo.packageName, '#')

                    val id = user.addUserSuffixToString(pojoScheme + appInfo.packageName + "/" + activityInfo.name, '/')

                    val appName = activityInfo.label.toString()

                    val packageName = appInfo.packageName
                    val activityName = activityInfo.name

                    app.id = id
                    app.appName = appName
                    app.packageName = packageName
                    app.activityName = activityName
                    app.userHandle = user

                    apps.add(app)


                    Log.d(TAG, " ,fullPackageName=" + fullPackageName +
                            " ,id=" + id +
                            " ,name=" + appName +
                            " ,packageName=" + packageName +
                            " ,activityName=" + activityName)

                }
            }
        } else {
            Log.d(TAG, "Enter NON - LOLLIPOP")

            val manager = this.packageManager

            val mainIntent = Intent(Intent.ACTION_MAIN, null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

            for (info in manager.queryIntentActivities(mainIntent, 0)) {
                val appInfo = info.activityInfo.applicationInfo

                val id = pojoScheme + appInfo.packageName + "/" + info.activityInfo.name
                val appName = info.loadLabel(manager).toString()

                val packageName = appInfo.packageName
                val activityName = info.activityInfo.name

                Log.d(TAG, " ,id=" + id +
                        " ,appName=" + appName +
                        " ,packageName=" + packageName +
                        " ,activityName=" + activityName)
            }
        }

        // Apply app sorting preference
        Collections.sort(apps, AppPojo.NameComparator())

        val recyclerView = findViewById(R.id.app_list) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter = AppAdapter(apps, this)
        recyclerView.adapter = adapter

    }

}
