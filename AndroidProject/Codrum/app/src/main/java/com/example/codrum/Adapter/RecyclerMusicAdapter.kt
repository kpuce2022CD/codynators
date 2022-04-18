package com.example.codrum.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.codrum.Model.Music
import com.example.codrum.R

class RecyclerMusicAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerMusicAdapter.ViewHolder>(){

    var items = mutableListOf<Music>()

    interface OnItemClickListener{
        fun onItemClick(v:View,data : Music, pos:Int)
    }
    private var listener : OnItemClickListener? = null

    fun setOnItemClickListener(listener : OnItemClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerMusicAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_music,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerMusicAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int =items.size

    inner class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        private var view : View = v

        fun bind(item : Music){
            view.findViewById<TextView>(R.id.music_title).text = item.title
            val pos = adapterPosition
            if(pos!=RecyclerView.NO_POSITION){
                view.findViewById<ConstraintLayout>(R.id.item_layout).setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }
        }
    }
}
