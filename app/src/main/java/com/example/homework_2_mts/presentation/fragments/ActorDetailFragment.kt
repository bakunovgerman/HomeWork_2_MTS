package com.example.homework_2_mts.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homework_2_mts.R
import com.example.homework_2_mts.databinding.FragmentActorDetailBinding
import com.example.homework_2_mts.domain.ActorDetailViewModel
import com.example.homework_2_mts.presentation.adapters.view_holders.MoviesViewHolder
import com.example.homework_2_mts.presentation.helpers.ViewStateLayout
import com.example.homework_2_mts.repository.retrofit.entities.actorDetail.ActorDetailResponse
import com.squareup.picasso.Picasso

class ActorDetailFragment : Fragment() {

    private var idActor: Long? = null
    private lateinit var binding: FragmentActorDetailBinding
    private lateinit var viewModel: ActorDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idActor = it.getLong(ID_ACTOR)
        }
        viewModel = ViewModelProvider(this).get(ActorDetailViewModel::class.java)
        initSubs()
    }

    private fun initSubs() {
        viewModel.actorDetailData.observe(this, { actorDetailData ->
            setData(actorDetailData)
        })
        viewModel.viewState.observe(this, { viewState ->
            setViewState(viewState)
        })
    }

    private fun setData(actorDetailData: ActorDetailResponse) = with(actorDetailData) {
        Picasso.get()
            .load(MoviesViewHolder.BASE_IMAGE_URL + profilePath)
            .into(binding.imgActorPhoto)
        binding.nameActorTextView.text = name
        binding.biographyTextView.text =
            if (biography != "")
                biography
            else
                getString(R.string.no_info_biography)
        binding.dateBirthTextView.text = birthday
        binding.placeBirthTextView.text = placeOfBirth
        binding.genderTextView.text = when (gender) {
            2 -> getString(R.string.man_gender)
            else -> getString(R.string.woman_gender)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActorDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        idActor?.let {
            viewModel.getActorDetail(it)
        }
    }

    private fun setViewState(viewStateLayout: ViewStateLayout) = with(viewStateLayout) {
        if (isDownloaded) {
            hideProgressBar()
        } else {
            showProgressBar()
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    companion object {
        const val ID_ACTOR = "id_actor"

        @JvmStatic
        fun newInstance(idActor: String) =
            ActorDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_ACTOR, idActor)
                }
            }
    }
}