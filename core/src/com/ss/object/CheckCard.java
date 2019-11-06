package com.ss.object;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.security.InvalidParameterException;

//public class CheckCard {
//  private static int[][] cache13 = new int[1287][5]; //pre-cache13 all 1287 possible 5 elements set of a 13 elements set :)
//  private static int[][] cache8 = new int[56][5];
//  private static int[][] cache7 = new int[21][5];
//  public static final ArrayMap<Integer, String> nameMap = new ArrayMap<>();
//  private static int valueMask = Integer.parseInt("00001111111111111", 2);
//  private static int typeMask = Integer.parseInt( "11110000000000000", 2);
//  private static ArrayMap<Integer, Integer> dupMap = new ArrayMap<>();
//  static {
//    preCache(13, cache13);
//    preCache(8, cache8);
//    preCache(7, cache7);
//  }
//
//  public static int check5(int[] cards) {
//    if (cards.length != 5) throw new InvalidParameterException("not enough card");
//    dupMap.clear();
//    int color = cards[0];
//    int value = cards[0];
//    int maxDup = 0;
//
//    Integer d = dupMap.get(cards[0]&valueMask);
//    dupMap.put(cards[0]&valueMask, (d == null) ? 0 : d + 1);
//
//    for (int i = 1; i < cards.length; i++) {
//      color &= cards[i];
//      value |= cards[i];
//
//      Integer _d = dupMap.get(cards[i]&valueMask);
//      dupMap.put(cards[i]&valueMask, (_d == null) ? 0 : _d + 1);
//      _d = dupMap.get(cards[i]&valueMask);
//      maxDup = maxDup < _d ? _d : maxDup;
//    }
//    int type = Integer.bitCount(color&typeMask)*5;
//    value &= valueMask;
//    switch (Integer.bitCount(value)){
//      case 2:
//        type += maxDup == 3 ? 7 : 6;
//        break;
//      case 3:
//        type += maxDup == 2 ? 3 : 2;
//        break;
//      case 4:
//        type += 1;
//        break;
//      case 5:
//        if ((value>>12) == 1 && (value & 15) == 15){
//          type += 4;
//          value = 15;
//          break;
//        }
//        while(value%2 == 0) value >>= 1;
//        type += value == 31 ? 4 : 0;
//        break;
//      default:
//        type += 0;
//        break;
//    }
//    return (type<<13) + value;
//  }
//
//  public static int[][] move(int[] pattern) {
//    int[][] res = new int[3][];
//    int[] low = move135(pattern);
//    int[] remain8 = _trim(pattern, low);
//    int[] mid = move85(remain8);
//    int[] high = _trim(remain8, mid);
//    res[0] = low;
//    res[1] = mid;
//    res[2] = high;
//    return res;
//  }
//
//  public static int compare5(int[] patternA, int[] patternB) {
//    if (patternA.length != 5 || patternB.length != 5)
//      throw new InvalidParameterException("not enough card");
//    int rA = check5(patternA);
//    int rB = check5(patternB);
//
//    int tA = rA&typeMask;
//    int tB = rB&typeMask;
//    if (tA > tB) return 1;
//    if (tA < tB) return -1;
//
//    int vA = rA&valueMask;
//    int vB = rB&valueMask;
//    if (vA > vB) return 1;
//    if (vA < vB) return -1;
//
//    int sA = 0,sB = 0,aA = 0, aB = 0;
//    for (int i = 0; i < 5; i++) {
//      sA += patternA[i];
//      sB += patternB[i];
//      aA += (patternA[i]&typeMask)*(patternA[i]&valueMask);
//      aB += (patternB[i]&typeMask)*(patternB[i]&valueMask);
//
//    }
//    if (sA > sB) return 1;
//    if (sA < sB) return -1;
//    if (aA > aB) return 1;
//    if (aA < aB) return -1;
//
//    for (int i = 0; i < 5; i++){
//      System.out.print(nameMap.get(patternA[i]) + " ");
//      System.out.print(nameMap.get(patternB[i]) + " ");
//      System.out.println();
//    }
//    System.out.println();
//    throw new InvalidParameterException("card duplicate");
//  }
//
//  public static int compare13(int[][] m1, int[][] m2) {
//    //make sure you input the right thing
//    int c11,c12,c21,c22,c13,c23;
//    c11 = check5(m1[0]);c12 = check5(m2[0]);
//    c21 = check5(m1[1]);c22 = check5(m2[1]);
//    c13 = check5(m1[2]);c23 = check5(m2[2]);
//    int res = 0;
//    res += Integer.compare(c11,c12);
//    res += Integer.compare(c21,c22);
//    res += Integer.compare(c13,c23);
//    return res;
//  }
//
//  static int[] move135(int[] pattern) {
//    if (pattern.length != 13) throw new InvalidParameterException("pattern must have 13 elements");
//    return _move(pattern, 1287, cache13);
//  }
//
//  static int[] move85(int[] pattern) {
//    if (pattern.length != 8) throw new InvalidParameterException("pattern must have 8 elements");
//    return _move(pattern, 56, cache8);
//  }
//
//  static int[] move75(int[] pattern) {
//    if (pattern.length != 7) throw new InvalidParameterException("pattern must have 7 elements");
//    return _move(pattern, 21, cache7);
//  }
//
//  public static Array<Integer> makeCards() {
//    Array<Integer> res = new Array<>();
//    nameMap.clear();
//    for (int i = 0; i < 13; i++)
//      for (int j = 0; j < 4; j++){
//        int v = 1 << i;
//        int c = (8 >> j) << 13;
//        int card = c | v;
//
//        String value = "";
//        switch (i) {
//          case 0: value = "2";break;
//          case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: value = (i + 2) + ""; break;
//          case 9: value = "J"; break;
//          case 10: value = "Q"; break;
//          case 11: value = "K"; break;
//          case 12: value = "A"; break;
//          default: break;
//        }
//
//        String color = "";
//        switch (c >> 13) {
//          case 8: color = "Bich"; break;
//          case 4: color = "Co"; break;
//          case 2: color = "Ro"; break;
//          case 1: color = "Chuon"; break;
//          default: break;
//        }
//        String cardName = value + color;
//        nameMap.put(card, cardName);
//        res.add(card);
//      }
////    res.shuffle();
//    return res;
//  }
//
//  ////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  private static int _compare5(int[] patternA, int[] patternB, int rA, int rB) {
//    int tA = rA&typeMask;
//    int tB = rB&typeMask;
//    if (tA > tB) return 1;
//    if (tA < tB) return -1;
//
//    int vA = rA&valueMask;
//    int vB = rB&valueMask;
//    if (vA > vB) return 1;
//    if (vA < vB) return -1;
//
//    int sA = 0,sB = 0,aA = 0, aB = 0;
//    for (int i = 0; i < 5; i++) {
//      sA += patternA[i];
//      sB += patternB[i];
//      aA += (patternA[i]&typeMask)*(patternA[i]&valueMask);
//      aB += (patternB[i]&typeMask)*(patternB[i]&valueMask);
//
//    }
//    if (sA > sB) return 1;
//    if (sA < sB) return -1;
//    if (aA > aB) return 1;
//    if (aA < aB) return -1;
//
//    for (int i = 0; i < 5; i++){
//      System.out.print(nameMap.get(patternA[i]) + " ");
//      System.out.print(nameMap.get(patternB[i]) + " ");
//      System.out.println();
//    }
//    System.out.println();
//    throw new InvalidParameterException("card duplicate");
//  }
//
//  private static int _maxDup(int[] cards) {
//    dupMap.clear();
//    int maxDup = 0;
//
//    int key;
//    for (int card : cards) {
//      key = card & valueMask;
//      Integer _d = dupMap.get(key);
//      dupMap.put(key, (_d == null) ? 0 : _d + 1);
//      _d = dupMap.get(key);
//      maxDup = maxDup < _d ? _d : maxDup;
//    }
//    return maxDup;
//  }
//
//  private static int _check5(int[] cards, int color, int value) {
//    int type = (color&typeMask) == 0 ? 0 : 5;
//    value &= valueMask;
//    switch (Integer.bitCount(value)){
//      case 2:
//        type += _maxDup(cards) == 3 ? 7 : 6;
//        break;
//      case 3:
//        type += _maxDup(cards) == 2 ? 3 : 2;
//        break;
//      case 4:
//        type += 1;
//        break;
//      case 5:
//        if ((value>>12) == 1 && (value & 15) == 15){
//          type += 4;
//          value = 15;
//          break;
//        }
//        while(value%2 == 0) value >>= 1;
//        type += value == 31 ? 4 : 0;
//        break;
//      default:
//        type += 0;
//        break;
//    }
//    return (type<<13) + value;
//  }
//
//  private static int[] _move(int[] pattern, int subset, int[][] cache) {
//    int[] buffer = new int[5];
//    int[] res = new int[5];
//    int rA,rB, c, v;
//    for (int _i = 0; _i < 5; _i++) res[_i] = pattern[cache[0][_i]];
//    rA = check5(res);
//    for (int i = 1; i < subset; i++) {
//      buffer[0] = c = v = pattern[cache[i][0]];
//      for (int j = 1; j < 5; j++) {
//        buffer[j] = pattern[cache[i][j]];
//        c &= buffer[j];
//        v |= buffer[j];
//      }
//      rB = _check5(buffer, c, v);
//      if (rB == 0) continue;
//      if (_compare5(res, buffer, rA, rB) < 0) {
//        System.arraycopy(buffer, 0, res, 0, buffer.length);
//        rA = rB;
//      }
//    }
//    return res;
//  }
//
//  private static int[] _trim(int[] src, int[] cut) {
//    int[] res = new int[src.length - cut.length];
//    boolean dup = false;
//    int idx = 0;
//    for (int i2 : src) {
//      for (int i1 : cut) if (i2 == i1) dup = true;
//      if (!dup) res[idx++] = i2;
//      dup = false;
//    }
//
//    return res;
//  }
//
//  private static void preCache(int maxSize, int[][] cache) {
//    int step = 1<<maxSize;
//    int _i = 0;
//    for (int i = 0; i < step; i++) {
//      if (Integer.bitCount(i) == 5) {
//        int _j = 0;
//        for (int j = 0; j < 13; j++)
//          if ((i&(1<<j)) > 0)
//            cache[_i][_j++] = j;
//        _i++;
//      }
//    }
//  }
//}
public class CheckCard {
  public static final ArrayMap<Integer, String> nameMap = new ArrayMap<>();
  private static int valueMask = Integer.parseInt("00001111111111111", 2);
  private static int typeMask = Integer.parseInt( "11110000000000000", 2);
  private static ArrayMap<Integer, Integer> dupMap = new ArrayMap<>();

  public static Array<Integer> move(Array<Integer> deck, Array<Integer> hand) {
    Array<Array<Integer>> sub3 = subset(deck, 3);
    Array<Array<Integer>> sub4 = subset(deck, 4);
    Array<Array<Integer>> sub44 = subset(deck, 4);

    for (Array<Integer> s : sub4) s.add(hand.get(0));
    for (Array<Integer> s : sub44) s.add(hand.get(1));

    for (Array<Integer> s : sub3)
      for (int i : hand) s.add(i);

    for (Array<Integer> s : sub3) sub4.add(s);
    for (Array<Integer> s : sub4) sub44.add(s);

    return findBiggest(sub44);
  }

  public static Array<Array<Integer>> subset(Array<Integer> cards, int subECount) {
    Array<Array<Integer>> res = new Array<>();
    int step = 1<<cards.size;
    for (int i = 0; i < step; i++)
      if (Integer.bitCount(i) == subECount) {
        Array<Integer> r = new Array<>();
        for (int j = 0; j < cards.size; j++) {
          if ((i & (1 << j)) > 0) {
            r.add(cards.get(j));
          }
        }
        res.add(r);
      }
    return res;
  }

  public static Array<Integer> findBiggest(Array<Integer> ...arg) {
    Array<Integer> result = null;
    Array<Array<Integer>> cardPacks = new Array<>();
    for (Array<Integer> a : arg) cardPacks.add(a);
    cardPacks.sort((c1, c2) -> compare5(c2, c1));
    result = cardPacks.get(0);
    return result;
  }

  public static Array<Integer> findBiggest(Array<Array<Integer>> cardPacks) {
    Array<Integer> result = null;
    cardPacks.sort((c1, c2) -> compare5(c2, c1));
    result = cardPacks.get(0);
    return result;
  }

  public static int check5(Array<Integer> cards) {
    if (cards.size != 5) throw new InvalidParameterException("not enough card");

    dupMap.clear();
    int color = cards.get(0);
    int value = cards.get(0);
    int maxDup = 0;

    Integer d = dupMap.get(cards.get(0)&valueMask);
    dupMap.put(cards.get(0)&valueMask, (d == null) ? 0 : d + 1);

    for (int i = 1; i < cards.size; i++) {
      color &= cards.get(i);
      value |= cards.get(i);

      Integer _d = dupMap.get(cards.get(i)&valueMask);
      dupMap.put(cards.get(i)&valueMask, (_d == null) ? 0 : _d + 1);
      _d = dupMap.get(cards.get(i)&valueMask);
      maxDup = maxDup < _d ? _d : maxDup;
    }
    int type = Integer.bitCount(color&typeMask)*5;
    value &= valueMask;
    switch (Integer.bitCount(value)){
      case 2:
        type += maxDup == 3 ? 7 : 6;
        break;
      case 3:
        type += maxDup == 2 ? 3 : 2;
        break;
      case 4:
        type += 1;
        break;
      case 5:
        if ((value>>12) == 1 && (value & 15) == 15){
          type += 4;
          value = 15;
          break;
        }
        while(value%2 == 0) value >>= 1;
        type += value == 31 ? 4 : 0;
        break;
      default:
        type += 0;
        break;
    }
    return (type<<13) + value;
  }

  public static int compare5(Array<Integer> patternA, Array<Integer> patternB) {
    if (patternA.size != 5 || patternB.size != 5)
      throw new InvalidParameterException("not enough card");
    int rA = check5(patternA);
    int rB = check5(patternB);

    int tA = rA&typeMask;
    int tB = rB&typeMask;
    if (tA > tB) return 1;
    if (tA < tB) return -1;

    int vA = rA&valueMask;
    int vB = rB&valueMask;
    if (vA > vB) return 1;
    if (vA < vB) return -1;

    int sA = 0,sB = 0,aA = 0, aB = 0;
    for (int i = 0; i < 5; i++) {
      sA += patternA.get(i);
      sB += patternB.get(i);
      aA += (patternA.get(i)&typeMask)*(patternA.get(i)&valueMask);
      aB += (patternB.get(i)&typeMask)*(patternB.get(i)&valueMask);

    }
    if (sA > sB) return 1;
    if (sA < sB) return -1;
    if (aA > aB) return 1;
    if (aA < aB) return -1;

    for (int i = 0; i < 5; i++){
      System.out.print(nameMap.get(patternA.get(i)) + " ");
      System.out.print(nameMap.get(patternB.get(i)) + " ");
      System.out.println();
    }
    System.out.println();
    throw new InvalidParameterException("card duplicate");
  }

  public static Array<Integer> makeCards() {
    Array<Integer> res = new Array<>();
    nameMap.clear();
    for (int i = 0; i < 13; i++)
      for (int j = 0; j < 4; j++){
        int v = 1 << i;
        int c = (8 >> j) << 13;
        int card = c | v;

        String value = "";
        switch (i) {
          case 0: value = "2";break;
          case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: value = (i + 2) + ""; break;
          case 9: value = "J"; break;
          case 10: value = "Q"; break;
          case 11: value = "K"; break;
          case 12: value = "A"; break;
          default: break;
        }

        String color = "";
        switch (c >> 13) {
          case 8: color = "Bich"; break;
          case 4: color = "Co"; break;
          case 2: color = "Ro"; break;
          case 1: color = "Chuon"; break;
          default: break;
        }
        String cardName = value + color;
        nameMap.put(card, cardName);
        res.add(card);
      }
   // res.shuffle();
    return res;
  }
}


//        cards = P.makeCards(); //tao bai
//        Array<Integer> deck = new Array<>();
//        Array<Integer> hand = new Array<>();
//        for (int i = 0; i < 5; i++) deck.add(cards.get(i)); //tao 5 la tren ban
//        for (int i = 5; i < 7; i++) hand.add(cards.get(i)); //tao 2 la tren tay

//        //tim 5 la lon nhat trong 7 la (5 tren ban 2 tren tay) dieu kien la
//        //phai co it nhat 1 la (nhieu nhat la 2) la tren tay
//        Array<Integer> biggest = P.move(deck, hand);
//
//        //in 5 la tren ban
//        for (Integer i : deck) System.out.print(P.nameMap.get(i) + " ");
//        System.out.println();
//        //in 5 la tren tay
//        for (Integer i : hand) System.out.print(P.nameMap.get(i) + " ");
//        System.out.println();
//
//        //in ket qua 5 la binh cao nhat lay tu 5 la tren ban + 2 la tren tay,
//        //ket qua luon co it nhat 1 (nhieu nhat 2) la tren tay
//        for (Integer i : biggest) System.out.print(P.nameMap.get(i) + " ");
//        System.out.println(m.get(P.check5(biggest)>>13));


//SO SANH NHIEU PLAYER
//  Array<Integer> a1 = new Array<>(); //5 la binh player 1
//  Array<Integer> a2 = new Array<>(); //5 la binh player 1
//  Array<Integer> a3 = new Array<>(); //5 la binh player 1
//  Array<Integer> a4 = new Array<>(); //5 la binh player 1
//  Array<Integer> a5 = new Array<>(); //5 la binh player 1
//
//
//  //tim ra trong a1 den a5 ai la nguoi co nc binh cao nhat
//  Array<Integer> b = P.findBiggest(a1,a2,a3,a4,a5);


