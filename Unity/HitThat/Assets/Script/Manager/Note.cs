using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Note : MonoBehaviour
{
    public float noteSpeed = 400; //노트 스피드

    UnityEngine.UI.Image noteImage;
    
    void Start()
    {
        noteImage = GetComponent<UnityEngine.UI.Image>();
    }

    // Update is called once per frame
    void Update()
    {
        transform.localPosition += Vector3.left * noteSpeed * Time.deltaTime; //1초당 왼쯕으로 노트 스피드 만큼
    }

      public void HideNote()
    {
        noteImage.enabled = false;
    }
}
