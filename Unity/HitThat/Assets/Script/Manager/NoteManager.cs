using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NoteManager : MonoBehaviour
{

    public int bpm = 0;

    double HihatcurrentTime = 0d;
    double SnarecurrentTime = 0d;
    double BasecurrentTime = 0d;

    [SerializeField] Transform tfHiHatNoteAppear = null; //HiHat노트가 생성될 위치 변수
    [SerializeField] GameObject goHiHatNote = null; //HiHat노트 prefab을 담을 변수

    [SerializeField] Transform tfSnareNoteAppear = null; //Snare노트가 생성될 위치 변수
    [SerializeField] GameObject goSnareNote = null; //Sanre노트 prefab을 담을 변수

    //[SerializeField] Transform tfCrashNoteAppear = null; //Crash노트가 생성될 위치 변수
    //[SerializeField] GameObject goCrashNote = null; //Crash노트 prefab을 담을 변수

    //[SerializeField] Transform tfHighTomNoteAppear = null; //HighTom노트가 생성될 위치 변수
    //[SerializeField] GameObject goHighTomNote = null; //HighTom노트 prefab을 담을 변수

    //[SerializeField] Transform tfMidTomNoteAppear = null; //MidTom노트가 생성될 위치 변수
    //[SerializeField] GameObject goMidTomNote = null; //MidTom노트 prefab을 담을 변수

    //[SerializeField] Transform tfLowTomNoteAppear = null; //LowTom노트가 생성될 위치 변수
    //[SerializeField] GameObject goLowTomNote = null; //LowTom노트 prefab을 담을 변수

    //[SerializeField] Transform tfRideNoteAppear = null; //Ride노트가 생성될 위치 변수
    //[SerializeField] GameObject goRideNote = null; //Ride노트 prefab을 담을 변수

    [SerializeField] Transform tfBaseNoteAppear = null; //Base노트가 생성될 위치 변수
    [SerializeField] GameObject goBaseNote = null; //Base노트 prefab을 담을 변수

    TimingManager theTimingManager;

    void Start()
    {
        theTimingManager = GetComponent<TimingManager>();
    }

    // Update is called once per frame
    void Update()
    {
        int SnareBpm = bpm-180;
        int BaseBpm = bpm-120;

        HihatcurrentTime += Time.deltaTime; //1초씩 증가
        SnarecurrentTime += Time.deltaTime; //1초씩 증가
        BasecurrentTime += Time.deltaTime; //1초씩 증가
        
        if(HihatcurrentTime >= 60d/bpm) //ex bpm이 120이면 0.5초당 노트가 하나씩
        {
            GameObject t_noteHitHat = Instantiate(goHiHatNote, tfHiHatNoteAppear.position, Quaternion.identity); // 노트 생성
            Debug.Log("하이앳 생성");
            // GameObject t_noteSnare = Instantiate(goSnareNote, tfSnareNoteAppear.position, Quaternion.identity); // 노트 생성
            //GameObject t_noteCrash = Instantiate(goCrashNote, tfCrashNoteAppear.position, Quaternion.identity); // 노트 생성
            //GameObject t_noteHighTom = Instantiate(goHighTomNote, tfHighTomNoteAppear.position, Quaternion.identity); // 노트 생성
            //GameObject t_noteMidTom = Instantiate(goMidTomNote, tfMidTomNoteAppear.position, Quaternion.identity); // 노트 생성
            //GameObject t_noteLowTom = Instantiate(goLowTomNote, tfLowTomNoteAppear.position, Quaternion.identity); // 노트 생성
            //GameObject t_noteRide = Instantiate(goRideNote, tfRideNoteAppear.position, Quaternion.identity); // 노트 생성
            //GameObject t_noteBase = Instantiate(goBaseNote, tfBaseNoteAppear.position, Quaternion.identity); // 노트 생성

            t_noteHitHat.transform.SetParent(this.transform);
            // t_noteSnare.transform.SetParent(this.transform);
           // t_noteCrash.transform.SetParent(this.transform);
           // t_noteHighTom.transform.SetParent(this.transform);
           // t_noteMidTom.transform.SetParent(this.transform);
           // t_noteLowTom.transform.SetParent(this.transform);
           // t_noteRide.transform.SetParent(this.transform);
           // t_noteBase.transform.SetParent(this.transform);

            theTimingManager.HitHatNoteList.Add(t_noteHitHat);
            // theTimingManager.SnareNoteList.Add(t_noteSnare);
           // theTimingManager.CrashNoteList.Add(t_noteCrash);
           // theTimingManager.HighTomNoteList.Add(t_noteHighTom);
           // theTimingManager.MidTomNoteList.Add(t_noteMidTom);
           // theTimingManager.LowTomNoteList.Add(t_noteLowTom);
           // theTimingManager.RideNoteList.Add(t_noteRide);
           // theTimingManager.BaseNoteList.Add(t_noteBase);

            HihatcurrentTime -= 60d/ bpm; //소수점 오차고려
        }

        if(BasecurrentTime >= 60d/BaseBpm)
        {
            GameObject t_noteBase = Instantiate(goBaseNote, tfBaseNoteAppear.position, Quaternion.identity); // 노트 생성
            Debug.Log("베이스 생성");
            t_noteBase.transform.SetParent(this.transform);
            theTimingManager.BaseNoteList.Add(t_noteBase);

            BasecurrentTime -= 60d/ BaseBpm; //소수점 오차고려
        }

        if(SnarecurrentTime >= 60d/SnareBpm)
        {
            GameObject t_noteSnare = Instantiate(goSnareNote, tfSnareNoteAppear.position, Quaternion.identity); // 노트 생성
            Debug.Log("스네어 생성");
            t_noteSnare.transform.SetParent(this.transform);
            theTimingManager.SnareNoteList.Add(t_noteSnare);

            SnarecurrentTime -= 60d/ SnareBpm; //소수점 오차고려
        }
    }


    private void OnTriggerExit2D(Collider2D collision) // 충돌 구현
    { 
        if(collision.CompareTag("HiHat"))
        {
            theTimingManager.HitHatNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
        if(collision.CompareTag("Snare"))
        {
            theTimingManager.SnareNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
        // if(collision.CompareTag("Crash"))
        // {
        //     theTimingManager.CrashNoteList.Remove(collision.gameObject);
        //     Destroy(collision.gameObject);
        // }
        // if(collision.CompareTag("HighTom"))
        // {
        //     theTimingManager.HighTomNoteList.Remove(collision.gameObject);
        //     Destroy(collision.gameObject);
        // }
        // if(collision.CompareTag("MidTom"))
        // {
        //     theTimingManager.MidTomNoteList.Remove(collision.gameObject);
        //     Destroy(collision.gameObject);
        // }
        // if(collision.CompareTag("LowTom"))
        // {
        //     theTimingManager.LowTomNoteList.Remove(collision.gameObject);
        //     Destroy(collision.gameObject);
        // }
        // if(collision.CompareTag("Ride"))
        // {
        //     theTimingManager.RideNoteList.Remove(collision.gameObject);
        //     Destroy(collision.gameObject);
        // }
        if(collision.CompareTag("Base"))
        {
            theTimingManager.BaseNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
    }
    
}
