package com.example.noteapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.ListItemBinding
import com.example.noteapp.model.Note
import kotlin.random.Random

class NotesAdapter(private val context: Context,
//                   private var listenerNote: NoteClickListener
                     private val onNoteClicked : (Note) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private lateinit var binding : ListItemBinding
    private val noteList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtilCallBack =object :DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
//          return NoteViewHolder(
//              LayoutInflater.from(parent.context).inflate(
//                  R.layout.list_item,parent,false
//              )
//          )

        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return NoteViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
     return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val note = differ.currentList[position]
        holder.itemView.apply {
            binding.tvNote.text = note.notes
            binding.tvTime.text = note.dates
            binding.tvTitle.text = note.title
        }
        binding.llCardView.setOnClickListener {
            setOnItemClick { notes ->
                onNoteClicked(note)
            }
        }
        binding.llCardView.setCardBackgroundColor(generateRandomColor())
    }


    private fun updateList(newList : List<Note>){

        fullList.clear()
        fullList.addAll(newList)

        noteList.clear()
        noteList.addAll(fullList)
        notifyDataSetChanged()

    }
    private fun filter(search : String){

        noteList.clear()

        for (item in fullList){
            if (item.title?.lowercase()?.contains(search) == true){
                noteList.add(item)
            }
        }
        notifyDataSetChanged()
    }


    private var onItemClickListener : ((Note) -> Unit) ? =null
    fun setOnItemClick(listener : (Note) -> Unit){
          onItemClickListener =listener
    }
    @SuppressLint("SuspiciousIndentation")
    private fun randomColor() : Int{

      val colorList = ArrayList<Int>()
          colorList.add(R.color.purple_200)
          colorList.add(R.color.purple_500)
          colorList.add(R.color.purple_700)
          colorList.add(R.color.teal_200)
          colorList.add(R.color.teal_700)

        val seed =System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(colorList.size)
        return randomIndex

    }
    fun generateRandomColor(): Int {
        return Color.rgb(
            (Math.random() * 256).toInt(),
            (Math.random() * 256).toInt(),
            (Math.random() * 256).toInt()
        )
    }


    interface NoteClickListener{
        fun onLongClicked(note: Note,cardView: CardView)
    }
}