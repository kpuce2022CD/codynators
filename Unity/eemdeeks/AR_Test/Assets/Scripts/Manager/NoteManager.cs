using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Threading;

public class NoteManager : MonoBehaviour
{

    public int bpm = 0;

    public string song;

    public bool ImageTarget; //드럼 인식되고 있는가? => 되고 있으면 true

    int index = 0;
    int madi = 1;

    int row;
    int column;

    List<int[]> note = new List<int[]>();

    double currentTime = 0d;

    [SerializeField] Transform tfHiHatNoteAppear = null; //HiHat노트가 생성될 위치 변수
    [SerializeField] GameObject goHiHatNote = null; //HiHat노트 prefab을 담을 변수

    [SerializeField] Transform tfSnareNoteAppear = null; //Snare노트가 생성될 위치 변수
    [SerializeField] GameObject goSnareNote = null; //Sanre노트 prefab을 담을 변수

    [SerializeField] Transform tfCrashNoteAppear = null; //Crash노트가 생성될 위치 변수
    [SerializeField] GameObject goCrashNote = null; //Crash노트 prefab을 담을 변수

    [SerializeField] Transform tfHighTomNoteAppear = null; //HighTom노트가 생성될 위치 변수
    [SerializeField] GameObject goHighTomNote = null; //HighTom노트 prefab을 담을 변수

    [SerializeField] Transform tfMidTomNoteAppear = null; //MidTom노트가 생성될 위치 변수
    [SerializeField] GameObject goMidTomNote = null; //MidTom노트 prefab을 담을 변수

    [SerializeField] Transform tfLowTomNoteAppear = null; //LowTom노트가 생성될 위치 변수
    [SerializeField] GameObject goLowTomNote = null; //LowTom노트 prefab을 담을 변수

    [SerializeField] Transform tfRideNoteAppear = null; //Ride노트가 생성될 위치 변수
    [SerializeField] GameObject goRideNote = null; //Ride노트 prefab을 담을 변수

    [SerializeField] Transform tfBaseNoteAppear = null; //Base노트가 생성될 위치 변수
    [SerializeField] GameObject goBaseNote = null; //Base노트 prefab을 담을 변수

    TimingManager theTimingManager;

    void Start()
    {
        List<Dictionary<string, object>> data = CSVReader.Read(song);
        // Debug.Log(data[0].Count.ToString()); //행(가로)
        // Debug.Log(data.Count.ToString()); //열(세로)
        row = data[0].Count;
        column = data.Count;

        // 노트 배열 초기화
        for (var i = 0; i < row; i++)
        {
            //Debug.Log("index"+(i).ToString() + ":"+data[i]["Hihat"]+" "+data[i]["Base"]+" "+data[i]["Snare"]);
            note.Add(new int[column]);
            for (int j = 0; j < column; j++)
            {
                switch (i)
                {
                    case 0:
                        note[i][j] = (int)data[j]["Hihat"];
                        Debug.Log("하이햇 들어옴");
                        break;
                    case 1:
                        note[i][j] = (int)data[j]["Snare"];
                        Debug.Log("스네어 들어옴");
                        break;
                    case 2:
                        note[i][j] = (int)data[j]["Crash"];
                        Debug.Log("크래시 들어옴");
                        break;
                    case 3:
                        note[i][j] = (int)data[j]["HighTom"];
                        Debug.Log("하이톰 들어옴");
                        break;
                    case 4:
                        note[i][j] = (int)data[j]["MidTom"];
                        Debug.Log("미드톰 들어옴");
                        break;
                    case 5:
                        note[i][j] = (int)data[j]["LowTom"];
                        Debug.Log("로우톰 들어옴");
                        break;
                    case 6:
                        note[i][j] = (int)data[j]["Ride"];
                        Debug.Log("라이드 들어옴");
                        break;
                    case 7:
                        note[i][j] = (int)data[j]["Base"];
                        Debug.Log("베이스 들어옴");
                        break;
                }
            }
        }

        for (int a = 0; a < row; a++)
        {
            for (int b = 0; b < column; b++)
            {
                Debug.Log("index" + "[" + (a).ToString() + "]" + "[" + (b).ToString() + "]" + ":" + note[a][b]);
            }
        }

        theTimingManager = GetComponent<TimingManager>();
        Thread.Sleep(2000);
    }

    // Update is called once per frame
    void Update()
    {

        if (ImageTarget)
        {
            currentTime += Time.deltaTime; //1초씩 증가

            // int [,] list =new int[3,4] {{1,1,1,1},{0,0,1,0},{1,0,1,0}};


            if (currentTime >= 60d / bpm) //ex bpm이 120이면 0.5초당 노트가 하나씩
            {
                if (note[0][index] == 1)
                {
                    GameObject t_noteHitHat = Instantiate(goHiHatNote, tfHiHatNoteAppear.position, Quaternion.identity); // 노트 생성
                                                                                                                         //Debug.Log("하이앳 생성");       
                    t_noteHitHat.transform.SetParent(this.transform);
                    theTimingManager.HitHatNoteList.Add(t_noteHitHat);
                }

                if (note[1][index] == 1)
                {
                    GameObject t_noteSnare = Instantiate(goSnareNote, tfSnareNoteAppear.position, Quaternion.identity); // 노트 생성
                                                                                                                        //Debug.Log("스네어 생성");
                    t_noteSnare.transform.SetParent(this.transform);
                    theTimingManager.SnareNoteList.Add(t_noteSnare);
                }

                if (note[2][index] == 1)
                {
                    GameObject t_noteCrash = Instantiate(goCrashNote, tfCrashNoteAppear.position, Quaternion.identity); // 노트 생성
                                                                                                                        //Debug.Log("크래시 생성");
                    t_noteCrash.transform.SetParent(this.transform);
                    theTimingManager.CrashNoteList.Add(t_noteCrash);
                }

                if (note[3][index] == 1)
                {
                    GameObject t_noteHighTom = Instantiate(goHighTomNote, tfHighTomNoteAppear.position, Quaternion.identity); // 노트 생성
                                                                                                                              //Debug.Log("하이톰 생성");       
                    t_noteHighTom.transform.SetParent(this.transform);
                    theTimingManager.HighTomNoteList.Add(t_noteHighTom);
                }

                if (note[4][index] == 1)
                {
                    GameObject t_noteMidTom = Instantiate(goMidTomNote, tfMidTomNoteAppear.position, Quaternion.identity); // 노트 생성
                                                                                                                           //Debug.Log("미드톰 생성");       
                    t_noteMidTom.transform.SetParent(this.transform);
                    theTimingManager.MidTomNoteList.Add(t_noteMidTom);
                }

                if (note[5][index] == 1)
                {
                    GameObject t_noteLowTom = Instantiate(goLowTomNote, tfLowTomNoteAppear.position, Quaternion.identity); // 노트 생성
                                                                                                                           //Debug.Log("로우톰 생성");       
                    t_noteLowTom.transform.SetParent(this.transform);
                    theTimingManager.LowTomNoteList.Add(t_noteLowTom);
                }

                if (note[6][index] == 1)
                {
                    GameObject t_noteRide = Instantiate(goRideNote, tfRideNoteAppear.position, Quaternion.identity); // 노트 생성
                                                                                                                     //Debug.Log("라이드 생성");       
                    t_noteRide.transform.SetParent(this.transform);
                    theTimingManager.RideNoteList.Add(t_noteRide);
                }

                if (note[7][index] == 1)
                {
                    GameObject t_noteBase = Instantiate(goBaseNote, tfBaseNoteAppear.position, Quaternion.identity); // 노트 생성
                                                                                                                     //Debug.Log("베이스 생성");       
                    t_noteBase.transform.SetParent(this.transform);
                    theTimingManager.BaseNoteList.Add(t_noteBase);
                }

                currentTime -= 60d / bpm; //소수점 오차고려
                index++;
                if (index == column)
                {
                    madi++;
                    index = 0;
                }
            }

            if (madi == 30)
            {
                //종료시키기
            }
        }
        
    }


    private void OnTriggerExit2D(Collider2D collision) // 충돌 구현
    {
        if (collision.CompareTag("HiHat"))
        {
            
            theTimingManager.HitHatNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
            
        }
        if (collision.CompareTag("Snare"))
        {
            theTimingManager.SnareNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
        if (collision.CompareTag("Crash"))
        {
            theTimingManager.CrashNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
        if (collision.CompareTag("HighTom"))
        {
            theTimingManager.HighTomNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
        if (collision.CompareTag("MidTom"))
        {
            theTimingManager.MidTomNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
        if (collision.CompareTag("LowTom"))
        {
            theTimingManager.LowTomNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
        if (collision.CompareTag("Ride"))
        {
            theTimingManager.RideNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
        if (collision.CompareTag("Base"))
        {
            theTimingManager.BaseNoteList.Remove(collision.gameObject);
            Destroy(collision.gameObject);
        }
    }
 
    public void OnTarget()
    {
        ImageTarget = true;
    }
    public void OffTarget()
    {
        ImageTarget = false;
    }
}
