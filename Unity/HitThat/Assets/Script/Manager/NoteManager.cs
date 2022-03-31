using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NoteManager : MonoBehaviour
{

    public int bpm = 0;

    int index = 0;
    int madi = 1;

    double currentTime = 0d;


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
        currentTime += Time.deltaTime; //1초씩 증가
        
        int [,] list =new int[3,4] {{1,1,1,1},{0,0,1,0},{1,0,1,0}};


        if(currentTime >= 60d/bpm) //ex bpm이 120이면 0.5초당 노트가 하나씩
        {
            if(list[0,index] == 1)
            {
                 GameObject t_noteHitHat = Instantiate(goHiHatNote, tfHiHatNoteAppear.position, Quaternion.identity); // 노트 생성
                Debug.Log("하이앳 생성");       
                t_noteHitHat.transform.SetParent(this.transform);
                theTimingManager.HitHatNoteList.Add(t_noteHitHat);
            }

            if(list[1,index]==1)
            {
                GameObject t_noteSnare = Instantiate(goSnareNote, tfSnareNoteAppear.position, Quaternion.identity); // 노트 생성
                Debug.Log("스네어 생성");
                t_noteSnare.transform.SetParent(this.transform);
                theTimingManager.SnareNoteList.Add(t_noteSnare);
            }

            if (list[2,index] ==1 ){
                GameObject t_noteBase = Instantiate(goBaseNote, tfBaseNoteAppear.position, Quaternion.identity); // 노트 생성
                Debug.Log("베이스 생성");
                t_noteBase.transform.SetParent(this.transform);
                theTimingManager.BaseNoteList.Add(t_noteBase);
            }
    
            currentTime -= 60d/ bpm; //소수점 오차고려
            index ++;
            if(index == 4){
                madi ++;
                index =0;
            }
        }

        if(madi == 30){
            //종료시키기
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
