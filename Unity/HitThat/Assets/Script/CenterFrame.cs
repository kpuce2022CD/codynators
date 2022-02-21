using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CenterFrame : MonoBehaviour
{

    AudioSource myAudio;
    bool musicStart = false; //첫번째 노트에만 노래 시작

    private void Start()
    {
        myAudio = GetComponent<AudioSource>();
    }

    private void OnTriggerExit2D(Collider2D collision)
    {
        if(!musicStart)
        if(collision.CompareTag("HiHat"))
        {
            myAudio.Play();
            musicStart=true;
        }
        // if(collision.CompareTag("Snare"))
        // {
        //     myAudio.Play();
        //     musicStart=true;
        // }
        // if(collision.CompareTag("Crash"))
        // {
        //     myAudio.Play();
        //     musicStart=true;
        // }
        // if(collision.CompareTag("HighTom"))
        // {
        //     myAudio.Play();
        //     musicStart=true;
        // }
        // if(collision.CompareTag("MidTom"))
        // {
        //     myAudio.Play();
        //     musicStart=true;
        // }
        // if(collision.CompareTag("LowTom"))
        // {
        //     myAudio.Play();
        //     musicStart=true;
        // }
        // if(collision.CompareTag("Ride"))
        // {
        //     myAudio.Play();
        //     musicStart=true;
        // }
        // if(collision.CompareTag("Base"))
        // {
        //     myAudio.Play();
        //     musicStart=true;
        // }
    }
}
