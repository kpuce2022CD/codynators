using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EffectManager : MonoBehaviour
{
    [SerializeField] Animator noteHitAnimaor = null; //노트 히트 이펙트
    string hit = "Hit";

    [SerializeField] Animator judgementAnimator = null;             //정확도 이펙트
    [SerializeField] UnityEngine.UI.Image judgementImage = null;    //교체할 이미지 변수
    [SerializeField] Sprite[] judgementSprite = null;               //정확도 배열

    public void JudgementEffect(int p_num)
    {
        judgementImage.sprite = judgementSprite[p_num];
        judgementAnimator.SetTrigger(hit);
    }

    public void NoteHitEffect()
    {
        noteHitAnimaor.SetTrigger(hit);
    }
}
