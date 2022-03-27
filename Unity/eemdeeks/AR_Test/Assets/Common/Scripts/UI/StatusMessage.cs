/*===============================================================================
Copyright (c) 2019 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

using System;
using System.Collections;
using UnityEngine;
using UnityEngine.UI;

public class StatusMessage : MonoBehaviour
{
    CanvasGroup mCanvasGroup;
    Text mMessage;
    bool mInitialized;
    static StatusMessage sStatusMessage;
    Coroutine mCoroutine;
    
    public static StatusMessage Instance
    {
        get
        {
            if (sStatusMessage != null)
                return sStatusMessage;
            var prefab = (GameObject)Resources.Load("StatusMessage");
            if (!prefab)
                return null;
            sStatusMessage = Instantiate(prefab.GetComponent<StatusMessage>());
            sStatusMessage.Init();
            return sStatusMessage;
            // If prefab not found, return null
        }
    }
    
    void Init()
    {
        if (mInitialized)
            return;
        mCanvasGroup = GetComponentInChildren<CanvasGroup>();
        mCanvasGroup.alpha = 0;
        mMessage = GetComponentInChildren<Text>();
        mMessage.text = "";
        mInitialized = true;
        var root = transform.GetChild(0).GetComponent<RectTransform>();
        var sam = FindObjectOfType<SafeAreaManager>();
        if (sam)
        {
            sam.AddSafeAreaRect(root, true, false);
        }
    }

    public void Display(string message, bool fadeOut = false)
    {
        // Check to see if there's an existing message first (i.e. Length > 0).
        // Return if the new message is a fadeOut message, otherwise overwrite.
        // Rule: Don't overwrite a non-fadeOut message with a fadeOut one.
        if (mMessage.text.Length > 0 && fadeOut)
        {
            return;
        }
        
        if (mCoroutine != null)
        {
            StopCoroutine(mCoroutine);
            mCoroutine = null;
        }
        
        mMessage.text = message;
        
        if (fadeOut)
        {
            mCanvasGroup.alpha = 1;
            mCoroutine = StartCoroutine(FadeOut());
        }
        else
            mCanvasGroup.alpha = (message.Length > 0) ? 1 : 0;
    }

    IEnumerator FadeOut()
    {
        yield return new WaitForSeconds(1f);
        
        for (float f = 1f; f >= 0; f -= 0.1f)
        {
            f = (float)Math.Round(f, 1);
            mCanvasGroup.alpha = (float)Math.Round(f, 1);
            yield return null;
        }
        // clear message
        mMessage.text = "";
    }
}
