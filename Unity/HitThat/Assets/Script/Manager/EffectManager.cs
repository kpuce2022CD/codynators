using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EffectManager : MonoBehaviour
{

    [SerializeField] Animator HiHatHitAnimator = null;
    [SerializeField] Animator SnareHitAnimator = null;
    [SerializeField] Animator CrashHitAnimator = null;
    [SerializeField] Animator HighTomHitAnimator = null;
    [SerializeField] Animator MidTomHitAnimator = null;
    [SerializeField] Animator LowTomHitAnimator = null;
    [SerializeField] Animator RideHitAnimator = null;
    [SerializeField] Animator BaseHitAnimator = null;

    string hit = "Hit";    

    public void HiHatHitEffect()
    {
        HiHatHitAnimator.SetTrigger(hit);
    }
    
    public void SnareHitEffect()
    {
        SnareHitAnimator.SetTrigger(hit);
    }

    public void CrashHitEffect()
    {
        CrashHitAnimator.SetTrigger(hit);
    }

    public void HighTomHitEffect()
    {
        HighTomHitAnimator.SetTrigger(hit);
    }

    public void MidTomHitEffect()
    {
        MidTomHitAnimator.SetTrigger(hit);
    }

    public void LowTomHitEffect()
    {
        LowTomHitAnimator.SetTrigger(hit);
    }

    public void RideHitEffect()
    {
        RideHitAnimator.SetTrigger(hit);
    }

    public void BaseHitEffect()
    {
        BaseHitAnimator.SetTrigger(hit);
    }

}
