using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HitFrame : MonoBehaviour
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
    }
}
