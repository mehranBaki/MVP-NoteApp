package ir.baky.mvp_noteapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.baky.mvp_noteapp.R
import ir.baky.mvp_noteapp.data.model.NoteEntity
import ir.baky.mvp_noteapp.data.repository.main.MainRepository
import ir.baky.mvp_noteapp.databinding.ActivityMainBinding
import ir.baky.mvp_noteapp.ui.note.NoteFragment
import ir.baky.mvp_noteapp.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContracts.View {
    //Binding
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var repository: MainRepository

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var presenter: MainPresenter

    //Other
    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //For the day mode theme only
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         //Init views
        binding.apply {
            //Set Action View
            setSupportActionBar(notesToolbar)
            //Note detail
            addNoteBtn.setOnClickListener { NoteFragment().show(supportFragmentManager,NoteFragment().tag) }
            //load All Notes
            presenter.loadAllNotes()
            //Clicks
            noteAdapter.setOnItemClickListener { noteEntity, state ->
                when(state){
                   EDIT -> {
                       val noteFragment = NoteFragment()
                       val bundle = Bundle()
                       bundle.putInt(BUNDLE_ID, noteEntity.id)
                       noteFragment.arguments = bundle
                       noteFragment.show(supportFragmentManager,NoteFragment().tag)
                   }
                    DELETE -> {
                        val entity = NoteEntity(noteEntity.id, noteEntity.title, noteEntity.desc, noteEntity.category, noteEntity.priority)
                        presenter.deleteNote(entity)
                    }
                }
            }
            //Filter
            notesToolbar.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.actionFilter -> {
                        filterByPriority()
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }

    override fun showAllNotes(notes: List<NoteEntity>) {
        binding.emptyLay.visibility = View.GONE
        binding.noteList.visibility = View.VISIBLE

        noteAdapter.setData(notes)
        binding.noteList.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    override fun showEmpty() {
        binding.emptyLay.visibility = View.VISIBLE
        binding.noteList.visibility = View.GONE
    }

    override fun deleteMessage() {
        Snackbar.make(binding.root, getString(R.string.deleted), Snackbar.LENGTH_SHORT).show()
    }

    private fun filterByPriority(){
        val builder = AlertDialog.Builder(this)
        val priorities = arrayOf(ALL, HIGH, NORMAL, LOW)
        builder.setSingleChoiceItems(priorities, selectedItem){ dialog, item ->
            when (item){
                0 -> {
                    presenter.loadAllNotes()
                }
                in 1..3 -> {
                    presenter.filterNote(priorities[item])
                }
            }
            selectedItem = item
            dialog.dismiss()
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.searchNote(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

}