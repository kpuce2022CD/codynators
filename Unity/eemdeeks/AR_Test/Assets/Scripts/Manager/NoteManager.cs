using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Threading;

public class NoteManager : MonoBehaviour
{

    public int bpm = 0;

    public string song;

    public bool ImageTarget; //�巳 �νĵǰ� �ִ°�? => �ǰ� ������ true

    int index = 0;
    int madi = 1;

    int row;
    int column;

    List<int[]> note = new List<int[]>();

    double currentTime = 0d;

    [SerializeField] Transform tfHiHatNoteAppear = null; //HiHat��Ʈ�� ������ ��ġ ����
    [SerializeField] GameObject goHiHatNote = null; //HiHat��Ʈ prefab�� ���� ����

    [SerializeField] Transform tfSnareNoteAppear = null; //Snare��Ʈ�� ������ ��ġ ����
    [SerializeField] GameObject goSnareNote = null; //Sanre��Ʈ prefab�� ���� ����

    [SerializeField] Transform tfCrashNoteAppear = null; //Crash��Ʈ�� ������ ��ġ ����
    [SerializeField] GameObject goCrashNote = null; //Crash��Ʈ prefab�� ���� ����

    [SerializeField] Transform tfHighTomNoteAppear = null; //HighTom��Ʈ�� ������ ��ġ ����
    [SerializeField] GameObject goHighTomNote = null; //HighTom��Ʈ prefab�� ���� ����

    [SerializeField] Transform tfMidTomNoteAppear = null; //MidTom��Ʈ�� ������ ��ġ ����
    [SerializeField] GameObject goMidTomNote = null; //MidTom��Ʈ prefab�� ���� ����

    [SerializeField] Transform tfLowTomNoteAppear = null; //LowTom��Ʈ�� ������ ��ġ ����
    [SerializeField] GameObject goLowTomNote = null; //LowTom��Ʈ prefab�� ���� ����

    [SerializeField] Transform tfRideNoteAppear = null; //Ride��Ʈ�� ������ ��ġ ����
    [SerializeField] GameObject goRideNote = null; //Ride��Ʈ prefab�� ���� ����

    [SerializeField] Transform tfBaseNoteAppear = null; //Base��Ʈ�� ������ ��ġ ����
    [SerializeField] GameObject goBaseNote = null; //Base��Ʈ prefab�� ���� ����

    TimingManager theTimingManager;

    void Start()
    {
        // List<Dictionary<string, object>> data = CSVReader.Read(song);
        // // Debug.Log(data[0].Count.ToString()); //��(����)
        // // Debug.Log(data.Count.ToString()); //��(����)
        // row = data[0].Count;
        // column = data.Count;

        // // ��Ʈ �迭 �ʱ�ȭ
        // for (var i = 0; i < row; i++)
        // {
        //     //Debug.Log("index"+(i).ToString() + ":"+data[i]["Hihat"]+" "+data[i]["Base"]+" "+data[i]["Snare"]);
        //     note.Add(new int[column]);
        //     for (int j = 0; j < column; j++)
        //     {
        //         switch (i)
        //         {
        //             case 0:
        //                 note[i][j] = (int)data[j]["Hihat"];
        //                 Debug.Log("������ ����");
        //                 break;
        //             case 1:
        //                 note[i][j] = (int)data[j]["Snare"];
        //                 Debug.Log("���׾� ����");
        //                 break;
        //             case 2:
        //                 note[i][j] = (int)data[j]["Crash"];
        //                 Debug.Log("ũ���� ����");
        //                 break;
        //             case 3:
        //                 note[i][j] = (int)data[j]["HighTom"];
        //                 Debug.Log("������ ����");
        //                 break;
        //             case 4:
        //                 note[i][j] = (int)data[j]["MidTom"];
        //                 Debug.Log("�̵��� ����");
        //                 break;
        //             case 5:
        //                 note[i][j] = (int)data[j]["LowTom"];
        //                 Debug.Log("�ο��� ����");
        //                 break;
        //             case 6:
        //                 note[i][j] = (int)data[j]["Ride"];
        //                 Debug.Log("���̵� ����");
        //                 break;
        //             case 7:
        //                 note[i][j] = (int)data[j]["Base"];
        //                 Debug.Log("���̽� ����");
        //                 break;
        //         }
        //     }
        // }

        // for (int a = 0; a < row; a++)
        // {
        //     for (int b = 0; b < column; b++)
        //     {
        //         Debug.Log("index" + "[" + (a).ToString() + "]" + "[" + (b).ToString() + "]" + ":" + note[a][b]);
        //     }
        // }

        // theTimingManager = GetComponent<TimingManager>();
        // Thread.Sleep(2000);
    }

    // Update is called once per frame
    void Update()
    {
    

        if (ImageTarget)
        {
            currentTime += Time.deltaTime; //1�ʾ� ����

            // int [,] list =new int[3,4] {{1,1,1,1},{0,0,1,0},{1,0,1,0}};


            if (currentTime >= 60d / bpm) //ex bpm�� 120�̸� 0.5�ʴ� ��Ʈ�� �ϳ���
            {
                if (note[0][index] == 1)
                {
                    GameObject t_noteHitHat = Instantiate(goHiHatNote, tfHiHatNoteAppear.position, Quaternion.identity); // ��Ʈ ����
                                                                                                                         //Debug.Log("���̾� ����");       
                    t_noteHitHat.transform.SetParent(this.transform);
                    theTimingManager.HitHatNoteList.Add(t_noteHitHat);
                }

                if (note[1][index] == 1)
                {
                    GameObject t_noteSnare = Instantiate(goSnareNote, tfSnareNoteAppear.position, Quaternion.identity); // ��Ʈ ����
                                                                                                                        //Debug.Log("���׾� ����");
                    t_noteSnare.transform.SetParent(this.transform);
                    theTimingManager.SnareNoteList.Add(t_noteSnare);
                }

                if (note[2][index] == 1)
                {
                    GameObject t_noteCrash = Instantiate(goCrashNote, tfCrashNoteAppear.position, Quaternion.identity); // ��Ʈ ����
                                                                                                                        //Debug.Log("ũ���� ����");
                    t_noteCrash.transform.SetParent(this.transform);
                    theTimingManager.CrashNoteList.Add(t_noteCrash);
                }

                if (note[3][index] == 1)
                {
                    GameObject t_noteHighTom = Instantiate(goHighTomNote, tfHighTomNoteAppear.position, Quaternion.identity); // ��Ʈ ����
                                                                                                                              //Debug.Log("������ ����");       
                    t_noteHighTom.transform.SetParent(this.transform);
                    theTimingManager.HighTomNoteList.Add(t_noteHighTom);
                }

                if (note[4][index] == 1)
                {
                    GameObject t_noteMidTom = Instantiate(goMidTomNote, tfMidTomNoteAppear.position, Quaternion.identity); // ��Ʈ ����
                                                                                                                           //Debug.Log("�̵��� ����");       
                    t_noteMidTom.transform.SetParent(this.transform);
                    theTimingManager.MidTomNoteList.Add(t_noteMidTom);
                }

                if (note[5][index] == 1)
                {
                    GameObject t_noteLowTom = Instantiate(goLowTomNote, tfLowTomNoteAppear.position, Quaternion.identity); // ��Ʈ ����
                                                                                                                           //Debug.Log("�ο��� ����");       
                    t_noteLowTom.transform.SetParent(this.transform);
                    theTimingManager.LowTomNoteList.Add(t_noteLowTom);
                }

                if (note[6][index] == 1)
                {
                    GameObject t_noteRide = Instantiate(goRideNote, tfRideNoteAppear.position, Quaternion.identity); // ��Ʈ ����
                                                                                                                     //Debug.Log("���̵� ����");       
                    t_noteRide.transform.SetParent(this.transform);
                    theTimingManager.RideNoteList.Add(t_noteRide);
                }

                if (note[7][index] == 1)
                {
                    GameObject t_noteBase = Instantiate(goBaseNote, tfBaseNoteAppear.position, Quaternion.identity); // ��Ʈ ����
                                                                                                                     //Debug.Log("���̽� ����");       
                    t_noteBase.transform.SetParent(this.transform);
                    theTimingManager.BaseNoteList.Add(t_noteBase);
                }

                currentTime -= 60d / bpm; //�Ҽ��� ��������
                index++;
                if (index == column)
                {
                    madi++;
                    index = 0;
                }
            }

            if (madi == 32)
            {
                //�����Ű��
            }
        }
        
    }


    private void OnTriggerExit2D(Collider2D collision) // �浹 ����
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
    
    public void setSong(string songTitle)
    {
        song = songTitle;
        // Debug.Log(song.ToString);
        List<Dictionary<string, object>> data = CSVReader.Read(song);
        // Debug.Log(data[0].Count.ToString()); 
        // Debug.Log(data.Count.ToString());
        row = data[0].Count;
        column = data.Count;

        // ��Ʈ �迭 �ʱ�ȭ
        for (var i = 0; i < row; i++)
        {
            Debug.Log("index"+(i).ToString() + ":"+data[i]["Hihat"]+" "+data[i]["Base"]+" "+data[i]["Snare"]);
            note.Add(new int[column]);
            for (int j = 0; j < column; j++)
            {
                switch (i)
                {
                    case 0:
                        note[i][j] = (int)data[j]["Hihat"];
                        Debug.Log("������ ����");
                        break;
                    case 1:
                        note[i][j] = (int)data[j]["Snare"];
                        Debug.Log("���׾� ����");
                        break;
                    case 2:
                        note[i][j] = (int)data[j]["Crash"];
                        Debug.Log("ũ���� ����");
                        break;
                    case 3:
                        note[i][j] = (int)data[j]["HighTom"];
                        Debug.Log("������ ����");
                        break;
                    case 4:
                        note[i][j] = (int)data[j]["MidTom"];
                        Debug.Log("�̵��� ����");
                        break;
                    case 5:
                        note[i][j] = (int)data[j]["LowTom"];
                        Debug.Log("�ο��� ����");
                        break;
                    case 6:
                        note[i][j] = (int)data[j]["Ride"];
                        Debug.Log("���̵� ����");
                        break;
                    case 7:
                        note[i][j] = (int)data[j]["Base"];
                        Debug.Log("���̽� ����");
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


}
