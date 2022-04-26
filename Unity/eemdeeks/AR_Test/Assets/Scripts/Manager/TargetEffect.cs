using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TargetEffect : MonoBehaviour
{
    [SerializeField] ParticleSystem TargetHiHat = null;
    [SerializeField] ParticleSystem TargetSnare = null;
    [SerializeField] ParticleSystem TargetCrash = null;
    [SerializeField] ParticleSystem TargetHighTom = null;
    [SerializeField] ParticleSystem TargetMidTom = null;
    [SerializeField] ParticleSystem TargetLowTom = null;
    [SerializeField] ParticleSystem TargetRide = null;
    [SerializeField] ParticleSystem TargetBase = null;


    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void OnTriggerExit2D(Collider2D collision) // 충돌 구현
    {
        if (collision.CompareTag("Snare"))
        {
            Debug.Log("스네어 쳐라");
            TargetSnare.Play();
        }
        if (collision.CompareTag("HiHat"))
        {
            TargetHiHat.Play();
        }
        if (collision.CompareTag("Crash"))
        {
            TargetCrash.Play();
        }
        if (collision.CompareTag("HighTom"))
        {
            TargetHighTom.Play();
        }
        if (collision.CompareTag("MidTom"))
        {
            TargetMidTom.Play();
        }
        if (collision.CompareTag("LowTom"))
        {
            TargetLowTom.Play();
        }
        if (collision.CompareTag("Ride"))
        {
            TargetRide.Play();
        }
        if (collision.CompareTag("Base"))
        {
            TargetBase.Play();
        }
    }

}
