using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    TimingManager theTimingManager;

    void Start()
    {
        theTimingManager = FindObjectOfType<TimingManager>();
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.A))
        {
            theTimingManager.HiHatCheckTiming();
        }
        if (Input.GetKeyDown(KeyCode.S))
        {
            theTimingManager.SnareCheckTiming();
        }

        // if (Input.GetKeyDown(KeyCode.D))
        // {
        //     theTimingManager.CrashCheckTiming();
        // }  
        // if (Input.GetKeyDown(KeyCode.F))
        // {
        //     theTimingManager.HighTomCheckTiming();
        // }  
        // if (Input.GetKeyDown(KeyCode.G))
        // {
        //     theTimingManager.MidTomCheckTiming();
        // }    
        // if (Input.GetKeyDown(KeyCode.H))
        // {
        //     theTimingManager.LowTomCheckTiming();
        // }  
        // if (Input.GetKeyDown(KeyCode.J))
        // {
        //     theTimingManager.RideCheckTiming();
        // } 

        if (Input.GetKeyDown(KeyCode.D))
        {
            theTimingManager.BaseCheckTiming();
        }  
    }
}
