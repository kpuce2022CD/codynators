package com.example.codrum.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codrum.Adapter.RecyclerMusicAdapter
import com.example.codrum.Dialog.LoadingDialog
import com.example.codrum.Model.Music
import com.example.codrum.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private val TAG = "ProfileFragment"
    lateinit var binding : FragmentProfileBinding
    lateinit var musicAdapter : RecyclerMusicAdapter
    private val fbRdb = Firebase.database
    val userUID = Firebase.auth.currentUser?.uid.toString()

    private val musicItem = mutableListOf<Music>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        initMusic()
        binding.btnRenew.setOnClickListener {
            renewMusic()
        }

        return binding.root
    }
    private fun initRecyclerMusic(context: Context){
        musicAdapter = RecyclerMusicAdapter(this.requireContext())
        binding.itemListMusic.adapter = musicAdapter
        musicAdapter.items = musicItem
        musicAdapter.setOnItemClickListener(object : RecyclerMusicAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: Music, pos: Int) {
                TODO("선택한 곡으로 유니티 실행")
            }
        })
    }

    private fun renewMusic(){
        val dialog = LoadingDialog(binding.root.context)
        dialog.show()
        fbRdb.getReference(userUID).get().addOnSuccessListener {
            for (c in it.children){
                if(musicItem.any(){it.title == c.key.toString()}){
                    continue
                }else{
                    musicItem.add(Music(c.key.toString()))
                }
            }
            dialog.dismiss()
            initRecyclerMusic(this.requireContext())
        }
    }
    private fun initMusic(){
        val dialog = LoadingDialog(binding.root.context)
        dialog.show()
        musicItem.add(Music("연습 1"))
        musicItem.add(Music("연습 2"))
        musicItem.add(Music("연습 3"))
        musicItem.add(Music("연습 4"))
        musicItem.add(Music("연습 5"))
        fbRdb.getReference(userUID).get().addOnSuccessListener {
            for (c in it.children){
                musicItem.add(Music(c.key.toString()))
            }
            dialog.dismiss()
            initRecyclerMusic(this.requireContext())
        }
    }
}