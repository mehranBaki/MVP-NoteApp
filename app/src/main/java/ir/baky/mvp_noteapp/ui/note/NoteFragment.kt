package ir.baky.mvp_noteapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ir.baky.mvp_noteapp.R
import ir.baky.mvp_noteapp.data.model.NoteEntity
import ir.baky.mvp_noteapp.data.repository.note.NoteRepository
import ir.baky.mvp_noteapp.databinding.FragmentNoteBinding
import ir.baky.mvp_noteapp.utils.*
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment(), NoteContracts.View {
    //binding
    private lateinit var binding: FragmentNoteBinding

    @Inject
    lateinit var entity: NoteEntity

    @Inject
    lateinit var repository: NoteRepository

    @Inject
    lateinit var presenter: NotePresenter

    //Other
    private lateinit var categoriesList: Array<String>
    private var category = "" //default
    private lateinit var prioritiesList: Array<String>
    private var priority = "" //default
    private var noteId = 0
    private var type = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Bundle
        noteId = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        type = if (noteId > 0) {
            EDIT
        } else {
            NEW
        }
        //init Views
        binding.apply {
            closeImg.setOnClickListener { this@NoteFragment.dismiss() }
            //Spinners
            categoriesSpinnerItems()
            prioritiesSpinnerItems()
            //set default value
            if (type == EDIT) {
                saveNote.setText(R.string.update)
                presenter.detailNote(noteId)
            }
            //Save btn
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()

                if (title.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(requireContext(), "Title/Description can not be empty!", Toast.LENGTH_LONG).show()
                } else {
                    //Entity
                    entity.id = noteId
                    entity.title = title
                    entity.desc = desc
                    entity.category = category
                    entity.priority = priority
                    //Save and Edit
                    if (type == NEW) {
                        presenter.saveNote(entity)
                    } else {
                        presenter.updateNote(entity)
                    }

                }
            }
        }
    }

    private fun categoriesSpinnerItems() {
        categoriesList = arrayOf(WORK, HOME, EDUCATION, HEALTH)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = adapter
        binding.categoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    category = categoriesList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Do what ever when nothing selected
                }
            }
    }

    private fun prioritiesSpinnerItems() {
        prioritiesList = arrayOf(HIGH, NORMAL, LOW)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, prioritiesList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.prioritySpinner.adapter = adapter
        binding.prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    priority = prioritiesList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Do what ever when nothing selected
                }
            }
    }

    override fun close() {
        this.dismiss()
    }

    override fun loadNote(note: NoteEntity) {
        if (this.isAdded) {
            requireActivity().runOnUiThread {
                binding.apply {
                    titleEdt.setText(note.title)
                    descEdt.setText(note.desc)
                    categoriesSpinner.setSelection(getIndex(categoriesList, note.category))
                    prioritySpinner.setSelection(getIndex(prioritiesList, note.priority))
                }
            }
        }
    }

    private fun getIndex(list: Array<String>, item: String): Int {
        var index = 0
        for (i in list.indices) {
            if (list[i] == item) {
                index = i
                break
            }
        }
        return index
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }



}