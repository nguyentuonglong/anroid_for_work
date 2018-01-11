package ntlong.androidforwork.com.adapter

import android.content.ComponentName
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ntlong.androidforwork.com.IconsHandler
import ntlong.androidforwork.com.R
import ntlong.androidforwork.com.pojo.AppPojo

/**
 * Created by ntlong on 1/11/18.
 */

class AppAdapter(private val mData: List<AppPojo>, context: Context) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    private val mIconsHandler: IconsHandler = IconsHandler(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false))
    }

    override fun onBindViewHolder(holder: AppAdapter.ViewHolder, position: Int) {
        holder.renderData(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val iconView: ImageView = itemView.findViewById(R.id.icon)
        private val appNameView: TextView = itemView.findViewById(R.id.name)
        private var className: ComponentName? = null

        fun renderData(data: AppPojo) {
            appNameView.text = data.appName

            //Process icon
            className = ComponentName(data.packageName, data.activityName)
            val icon = mIconsHandler.getDrawableIconForPackage(className, data.userHandle)
            iconView.setImageDrawable(icon)
        }


    }
}
