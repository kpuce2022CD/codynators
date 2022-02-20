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
        //Space 입력했을 경우
        if (Input.GetKeyDown(KeyCode.Space))
        {
            //판정체크.
            theTimingManager.CheckTiming();
        }
    }
}
