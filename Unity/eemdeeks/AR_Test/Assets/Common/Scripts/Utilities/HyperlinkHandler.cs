/*===============================================================================
Copyright (c) 2018 PTC Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

using UnityEngine;
using UnityEngine.EventSystems;
using TMPro;

public class HyperlinkHandler : MonoBehaviour, IPointerClickHandler, IPointerUpHandler
{
    TextMeshProUGUI mTextMeshPro;
    Camera mCamera;
    
    void Start()
    {
        mTextMeshPro = GetComponentInChildren<TextMeshProUGUI>();

        // Get a reference to the camera if Canvas Render Mode is not ScreenSpace Overlay
        var canvas = GetComponentInParent<Canvas>();
        mCamera = (canvas.renderMode == RenderMode.ScreenSpaceOverlay) ?
            null : canvas.worldCamera;
    }
    
    public void OnPointerClick(PointerEventData eventData)
    {
    }

    public void OnPointerUp(PointerEventData eventData)
    {
        CheckIfLinkAndOpenURL();
    }
    
    void CheckIfLinkAndOpenURL()
    {
        var linkIndex = TMP_TextUtilities.FindIntersectingLink(mTextMeshPro, Input.mousePosition, mCamera);

        if (linkIndex != -1)
        {
            var linkInfo = mTextMeshPro.textInfo.linkInfo[linkIndex];
            Application.OpenURL(linkInfo.GetLinkID());
        }
    }
}
