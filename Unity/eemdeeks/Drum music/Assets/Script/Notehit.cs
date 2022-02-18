using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Notehit : MonoBehaviour
{

    AudioSource myAudio;
    bool musicStart = false;

    // Start is called before the first frame update
    private void Start()
    {
        myAudio = GetComponent<AudioSource>();
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (!musicStart)
        {
            if (collision.CompareTag("Note"))
            {
                myAudio.Play();
                musicStart = true;
            }
        }
    }

   
}
