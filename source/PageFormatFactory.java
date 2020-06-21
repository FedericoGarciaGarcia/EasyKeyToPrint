/**
 * 
 * JFreeReport : a free Java reporting library
 * 
 *
 * Project Info:  http://reporting.pentaho.org/
 *
 * (C) Copyright 2001-2007, by Object Refinery Ltd, Pentaho Corporation and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ------------
 * PageFormatFactory.java
 * ------------
 * (C) Copyright 2001-2007, by Object Refinery Ltd, Pentaho Corporation and Contributors.
 */

import java.awt.Insets;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.lang.reflect.Field;

//import sun.rmi.runtime.Log;


/**
 * The PageFormatFactory is used to create PageFormats on a higher level. The Factory contains templates for all
 * PageSizes defined by Adobe:
 * <p/>
 * <a href="http://partners.adobe.com/asn/developer/pdfs/tn/5003.PPD_Spec_v4.3.pdf" >Postscript Specifications</a>
 * <p/>
 * Usage for creating an printjob on A4 paper with 2.5 cm border:
 * <pre>
 * Paper paper = PageFormatFactory.createPaper (PageSize.A4);
 * PageFormatFactory.setBordersMm (paper, 25, 25, 25, 25);
 * PageFormat format = PageFormatFactory.createPageFormat (paper, PageFormat.PORTRAIT);
 * </code>
 * <p/>
 * Defining a pageformat can be an ugly task and full of dependencies. The call to
 * PageFormatFactory.setBorders(...) will setup the paper's border and always assumes
 * that the paper is laid out in Portrait.
 * <p/>
 * Changing the PageFormat's orientation does not change the PageFormat's paper object,
 * but it changes the way, how the paper object is interpreted.
 *
 * @author Thomas Morgner
 */
public final class PageFormatFactory
{

  /**
   * Constant for dots per inch.
   *
   * @deprecated Not used anywhere.
   */
  public static final int DOTS_PER_INCH = 72;

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER11X17 = {792, 1224};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER10X11 = {720, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER10X13 = {720, 936};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER10X14 = {720, 1008};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER12X11 = {864, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER15X11 = {1080, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER7X9 = {504, 648};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER8X10 = {576, 720};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER9X11 = {648, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PAPER9X12 = {648, 864};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A0 = {2384, 3370};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A1 = {1684, 2384};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A2 = {1191, 1684};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A3 = {842, 1191};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A3_TRANSVERSE = {842, 1191};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A3_EXTRA = {913, 1262};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A3_EXTRATRANSVERSE = {913, 1262};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A3_ROTATED = {1191, 842};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A4 = {595, 842};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A4_TRANSVERSE = {595, 842};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A4_EXTRA = {667, 914};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A4_PLUS = {595, 936};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A4_ROTATED = {842, 595};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A4_SMALL = {595, 842};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A5 = {420, 595};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A5_TRANSVERSE = {420, 595};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A5_EXTRA = {492, 668};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A5_ROTATED = {595, 420};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A6 = {297, 420};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A6_ROTATED = {420, 297};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A7 = {210, 297};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A8 = {148, 210};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A9 = {105, 148};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] A10 = {73, 105};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ANSIC = {1224, 1584};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ANSID = {1584, 2448};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ANSIE = {2448, 3168};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ARCHA = {648, 864};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ARCHB = {864, 1296};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ARCHC = {1296, 1728};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ARCHD = {1728, 2592};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ARCHE = {2592, 3456};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B0 = {2920, 4127};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B1 = {2064, 2920};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B2 = {1460, 2064};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B3 = {1032, 1460};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B4 = {729, 1032};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B4_ROTATED = {1032, 729};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B5 = {516, 729};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B5_TRANSVERSE = {516, 729};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B5_ROTATED = {729, 516};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B6 = {363, 516};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B6_ROTATED = {516, 363};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B7 = {258, 363};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B8 = {181, 258};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B9 = {127, 181};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] B10 = {91, 127};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] C4 = {649, 918};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] C5 = {459, 649};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] C6 = {323, 459};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] COMM10 = {297, 684};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] DL = {312, 624};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] DOUBLEPOSTCARD = {567, 419};  // should be 419.5, but I ignore that..

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] DOUBLEPOSTCARD_ROTATED = {419, 567};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENV9 = {279, 639};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENV10 = {297, 684};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENV11 = {324, 747};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENV12 = {342, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENV14 = {360, 828};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC0 = {2599, 3676};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC1 = {1837, 2599};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC2 = {1298, 1837};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC3 = {918, 1296};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC4 = {649, 918};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC5 = {459, 649};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC6 = {323, 459};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC65 = {324, 648};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVC7 = {230, 323};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVCHOU3 = {340, 666};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVCHOU3_ROTATED = {666, 340};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVCHOU4 = {255, 581};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVCHOU4_ROTATED = {581, 255};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVDL = {312, 624};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVINVITE = {624, 624};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVISOB4 = {708, 1001};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVISOB5 = {499, 709};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVISOB6 = {499, 354};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVITALIAN = {312, 652};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVELOPE = {312, 652};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVKAKU2 = {680, 941};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVKAKU2_ROTATED = {941, 680};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVKAKU3 = {612, 785};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVKAKU3_ROTATED = {785, 612};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVMONARCH = {279, 540};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPERSONAL = {261, 468};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC1 = {289, 468};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC1_ROTATED = {468, 289};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC2 = {289, 499};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC2_ROTATED = {499, 289};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC3 = {354, 499};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC3_ROTATED = {499, 354};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC4 = {312, 590};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC4_ROTATED = {590, 312};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC5 = {312, 624};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC5_ROTATED = {624, 312};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC6 = {340, 652};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC6_ROTATED = {652, 340};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC7 = {454, 652};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC7_ROTATED = {652, 454};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC8 = {340, 876};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC8_ROTATED = {876, 340};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC9 = {649, 918};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC9_ROTATED = {918, 649};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC10 = {918, 1298};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVPRC10_ROTATED = {1298, 918};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVYOU4 = {298, 666};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ENVYOU4_ROTATED = {666, 298};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] EXECUTIVE = {522, 756};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] FANFOLDUS = {1071, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] FANFOLDGERMAN = {612, 864};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] FANFOLDGERMANLEGAL = {612, 936};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] FOLIO = {595, 935};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB0 = {2835, 4008};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB1 = {2004, 2835};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB2 = {1417, 2004};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB3 = {1001, 1417};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB4 = {709, 1001};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB5 = {499, 709};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB5_EXTRA = {570, 782};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB6 = {354, 499};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB7 = {249, 354};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB8 = {176, 249};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB9 = {125, 176};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] ISOB10 = {88, 125};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LEDGER = {1224, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LEGAL = {612, 1008};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LEGAL_EXTRA = {684, 1080};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LETTER = {612, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LETTER_TRANSVERSE = {612, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LETTER_EXTRA = {684, 864};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LETTER_EXTRATRANSVERSE = {684, 864};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LETTER_PLUS = {612, 914};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LETTER_ROTATED = {792, 612};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] LETTER_SMALL = {612, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] MONARCH = ENVMONARCH;

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] NOTE = {612, 792};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] POSTCARD = {284, 419};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] POSTCARD_ROTATED = {419, 284};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PRC16K = {414, 610};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PRC16K_ROTATED = {610, 414};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PRC32K = {275, 428};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PRC32K_ROTATED = {428, 275};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PRC32K_BIG = {275, 428};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] PRC32K_BIGROTATED = {428, 275};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] QUARTO = {610, 780};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] STATEMENT = {396, 612};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] SUPERA = {643, 1009};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] SUPERB = {864, 1380};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] TABLOID = {792, 1224};

  /**
   * A standard paper size.
   *
   * @deprecated Using public static arrays is dangerous.
   */
  public static final int[] TABLOIDEXTRA = {864, 1296};

  /**
   * A single instance of the factory.
   */
  private static PageFormatFactory singleton;

  /**
   * Default constructor.
   */
  private PageFormatFactory()
  {
  }

  /**
   * Returns a single instance of the factory.
   *
   * @return an instance of a PageFormatFactory.
   */
  public static PageFormatFactory getInstance()
  {
    if (singleton == null)
    {
      singleton = new PageFormatFactory();
    }
    return singleton;
  }

  /**
   * Creates a paper by using the paper size in points found in the int-array. The array must have a length of 2 and the
   * first value of this array has to contain the width and the second the height parameter. The created Paper has no
   * ImagableArea defined.
   *
   * @param papersize the definition of the papersize in a 2-element int-array
   * @return the created paper
   */
  public Paper createPaper(final int[] papersize)
  {
    if (papersize.length != 2)
    {
      throw new IllegalArgumentException("Paper must have a width and a height");
    }

    return createPaper(papersize[0], papersize[1]);
  }

  /**
   * Creates a paper by using the paper size in points found in the int-array. The array must have a length of 2 and the
   * first value of this array has to contain the width and the second the height parameter. The created Paper has no
   * ImagableArea defined.
   *
   * @param papersize the definition of the papersize in a 2-element int-array
   * @return the created paper
   */
  public Paper createPaper(final PageSize papersize)
  {
    return createPaper(papersize.getWidth(), papersize.getHeight());
  }

  /**
   * Creates a paper by using the paper size in points. The created Paper has no ImagableArea defined.
   *
   * @param width  the width of the paper in points
   * @param height the height of the paper in points
   * @return the created paper
   * @deprecated Use the double version instead.
   */
  public Paper createPaper(final int width, final int height)
  {
    return createPaper((double) width, (double) height);
  }

  /**
   * Creates a paper by using the paper size in points. The created Paper has no ImagableArea defined.
   *
   * @param width  the width of the paper in points
   * @param height the height of the paper in points
   * @return the created paper
   */
  public Paper createPaper(final double width, final double height)
  {
    final Paper p = new Paper();
    p.setSize(width, height);
    setBorders(p, 0, 0, 0, 0);
    return p;
  }

  /**
   * Defines the imageable area of the given paper by adjusting the border around the imagable area. The bordersizes are
   * given in points.
   *
   * @param paper  the paper that should be modified
   * @param top    the bordersize of the top-border
   * @param left   the border in points in the left
   * @param bottom the border in points in the bottom
   * @param right  the border in points in the right
   */
  public void setBorders(final Paper paper, final double top,
                         final double left, final double bottom, final double right)
  {
    final double w = paper.getWidth() - (right + left);
    final double h = paper.getHeight() - (bottom + top);
    paper.setImageableArea(left, top, w, h);
  }

  /**
   * Defines the imageable area of the given paper by adjusting the border around the imagable area. The bordersizes are
   * given in inches.
   *
   * @param paper  the paper that should be modified
   * @param top    the bordersize of the top-border
   * @param left   the border in points in the left
   * @param bottom the border in points in the bottom
   * @param right  the border in points in the right
   */
  public void setBordersInch
      (final Paper paper, final double top, final double left,
       final double bottom, final double right)
  {
    setBorders(paper, convertInchToPoints(top), convertInchToPoints(left),
        convertInchToPoints(bottom), convertInchToPoints(right));
  }

  /**
   * Defines the imageable area of the given paper by adjusting the border around the imagable area. The bordersizes are
   * given in millimeters.
   *
   * @param paper  the paper that should be modified
   * @param top    the bordersize of the top-border
   * @param left   the border in points in the left
   * @param bottom the border in points in the bottom
   * @param right  the border in points in the right
   */
  public void setBordersMm
      (final Paper paper, final double top, final double left,
       final double bottom, final double right)
  {
    setBorders(paper, convertMmToPoints(top), convertMmToPoints(left),
        convertMmToPoints(bottom), convertMmToPoints(right));
  }

  /**
   * Converts the given inch value to a valid point-value.
   *
   * @param inches the size in inch
   * @return the size in points
   */
  public double convertInchToPoints(final double inches)
  {
    return inches * 72.0f;
  }

  /**
   * Converts the given millimeter value to a valid point-value.
   *
   * @param mm the size in inch
   * @return the size in points
   */
  public double convertMmToPoints(final double mm)
  {
    return mm * (72.0d / 254.0d) * 10;
  }

  /**
   * Creates a new pageformat using the given paper and the given orientation.
   *
   * @param paper       the paper to use in the new pageformat
   * @param orientation one of PageFormat.PORTRAIT, PageFormat.LANDSCAPE or PageFormat.REVERSE_LANDSCAPE
   * @return the created Pageformat
   * @throws NullPointerException if the paper given was null
   */
  public PageFormat createPageFormat(final Paper paper, final int orientation)
  {
    if (paper == null)
    {
      throw new NullPointerException("Paper given must not be null");
    }
    final PageFormat pf = new PageFormat();
    pf.setPaper(paper);
    pf.setOrientation(orientation);
    return pf;
  }

  /**
   * Creates a paper by looking up the given Uppercase name in this classes defined constants. The value if looked up by
   * introspection, if the value is not defined in this class, null is returned.
   *
   * @param name the name of the constant defining the papersize
   * @return the defined paper or null, if the name was invalid.
   */
  public Paper createPaper(final String name)
  {
    try
    {
      final Field f = PageSize.class.getDeclaredField(name);
      final Object o = f.get(null);
      if (o instanceof PageSize == false)
      {
        // Log.debug ("Is no valid pageformat definition");
        return null;
      }
      final PageSize pageformat = (PageSize) o;
      return createPaper(pageformat);
    }
    catch (NoSuchFieldException nfe)
    {
      // Log.debug ("There is no pageformat " + name + " defined.");
      return null;
    }
    catch (IllegalAccessException aie)
    {
      // Log.debug ("There is no pageformat " + name + " accessible.");
      return null;
    }
  }

  /**
   * Logs the page format.
   *
   * @param pf the page format.
   */
  public static void logPageFormat(final PageFormat pf)
  {
    System.out.println("PageFormat: Width: " + pf.getWidth() + " Height: " + pf.getHeight());
    System.out.println("PageFormat: Image: X " + pf.getImageableX()
        + " Y " + pf.getImageableY()
        + " W: " + pf.getImageableWidth()
        + " H: " + pf.getImageableHeight());
    System.out.println("PageFormat: Margins: X " + pf.getImageableX()
        + " Y " + pf.getImageableY()
        + " X2: " + (pf.getImageableWidth() + pf.getImageableX())
        + " Y2: " + (pf.getImageableHeight() + pf.getImageableY()));
  }

  /**
   * Logs the paper size.
   *
   * @param pf the paper size.
   */
  public static void logPaper(final Paper pf)
  {
    System.out.println("Paper: Width: " + pf.getWidth() + " Height: " + pf.getHeight());
    System.out.println("Paper: Image: X " + pf.getImageableX()
        + " Y " + pf.getImageableY()
        + " H: " + pf.getImageableHeight()
        + " W: " + pf.getImageableWidth());
  }

  /**
   * Tests, whether the given two page format objects are equal.
   *
   * @param pf1 the first page format that should be compared.
   * @param pf2 the second page format that should be compared.
   * @return true, if both page formats are equal, false otherwise.
   */
  public static boolean isEqual(final PageFormat pf1, final PageFormat pf2)
  {
    if (pf1 == pf2)
    {
      return true;
    }
    if (pf1 == null || pf2 == null)
    {
      return false;
    }

    if (pf1.getOrientation() != pf2.getOrientation())
    {
      return false;
    }
    final Paper p1 = pf1.getPaper();
    final Paper p2 = pf2.getPaper();

    if (p1.getWidth() != p2.getWidth())
    {
      return false;
    }
    if (p1.getHeight() != p2.getHeight())
    {
      return false;
    }
    if (p1.getImageableX() != p2.getImageableX())
    {
      return false;
    }
    if (p1.getImageableY() != p2.getImageableY())
    {
      return false;
    }
    if (p1.getImageableWidth() != p2.getImageableWidth())
    {
      return false;
    }
    if (p1.getImageableHeight() != p2.getImageableHeight())
    {
      return false;
    }
    return true;
  }

  /**
   * Returns the left border of the given paper.
   *
   * @param p the paper that defines the borders.
   * @return the left border.
   */
  public double getLeftBorder(final Paper p)
  {
    return p.getImageableX();
  }

  /**
   * Returns the right border of the given paper.
   *
   * @param p the paper that defines the borders.
   * @return the right border.
   */
  public double getRightBorder(final Paper p)
  {
    return p.getWidth() - (p.getImageableX() + p.getImageableWidth());
  }

  /**
   * Returns the top border of the given paper.
   *
   * @param p the paper that defines the borders.
   * @return the top border.
   */
  public double getTopBorder(final Paper p)
  {
    return p.getImageableY();
  }

  /**
   * Returns the bottom border of the given paper.
   *
   * @param p the paper that defines the borders.
   * @return the bottom border.
   */
  public double getBottomBorder(final Paper p)
  {
    return p.getHeight() - (p.getImageableY() + p.getImageableHeight());
  }

  /**
   * Resolves a page format, so that the result can be serialized.
   *
   * @param format the page format that should be prepared for serialisation.
   * @return the prepared page format data.
   * @deprecated This functionality is part of JCommon-Serializer
   */
  public Object[] resolvePageFormat(final PageFormat format)
  {
    final Integer orientation = new Integer(format.getOrientation());
    final Paper p = format.getPaper();
    final float[] fdim = new float[]{(float) p.getWidth(), (float) p.getHeight()};
    final float[] rect = new float[]{(float) p.getImageableX(),
        (float) p.getImageableY(),
        (float) p.getImageableWidth(),
        (float) p.getImageableHeight()};
    return new Object[]{orientation, fdim, rect};
  }

  /**
   * Restores a page format after it has been serialized.
   *
   * @param data the serialized page format data.
   * @return the restored page format.
   * @deprecated This functionality is part of JCommon-Serializer
   */
  public PageFormat createPageFormat(final Object[] data)
  {
    final Integer orientation = (Integer) data[0];
    final float[] dim = (float[]) data[1];
    final float[] rect = (float[]) data[2];
    final Paper p = new Paper();
    p.setSize(dim[0], dim[1]);
    p.setImageableArea(rect[0], rect[1], rect[2], rect[3]);
    final PageFormat format = new PageFormat();
    format.setPaper(p);
    format.setOrientation(orientation.intValue());
    return format;
  }

  public Insets getPageMargins (final PageFormat format)
  {

    final int marginLeft = (int) format.getImageableX();
    final int marginRight = (int)
                (format.getWidth() - format.getImageableWidth() - format.getImageableX());
    final int marginTop = (int) (format.getImageableY());
    final int marginBottom = (int)
                (format.getHeight() - format.getImageableHeight() - format.getImageableY());
    return new Insets(marginTop, marginLeft, marginBottom, marginRight);
  }
}

/**
 * 
 * JFreeReport : a free Java reporting library
 * 
 *
 * Project Info:  http://reporting.pentaho.org/
 *
 * (C) Copyright 2001-2007, by Object Refinery Ltd, Pentaho Corporation and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ------------
 * PageSize.java
 * ------------
 * (C) Copyright 2001-2007, by Object Refinery Ltd, Pentaho Corporation and Contributors.
 */


/**
 * A class defining a page-dimension.
 *
 * @author Thomas Morgner
 */
final class PageSize
{
  /**
   * A standard paper size.
   */
  public static final PageSize PAPER11X17 = new PageSize(792, 1224);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER10X11 = new PageSize(720, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER10X13 = new PageSize(720, 936);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER10X14 = new PageSize(720, 1008);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER12X11 = new PageSize(864, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER15X11 = new PageSize(1080, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER7X9 = new PageSize(504, 648);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER8X10 = new PageSize(576, 720);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER9X11 = new PageSize(648, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize PAPER9X12 = new PageSize(648, 864);

  /**
   * A standard paper size.
   */
  public static final PageSize A0 = new PageSize(2384, 3370);

  /**
   * A standard paper size.
   */
  public static final PageSize A1 = new PageSize(1684, 2384);

  /**
   * A standard paper size.
   */
  public static final PageSize A2 = new PageSize(1191, 1684);

  /**
   * A standard paper size.
   */
  public static final PageSize A3 = new PageSize(842, 1191);

  /**
   * A standard paper size.
   */
  public static final PageSize A3_TRANSVERSE = new PageSize(842, 1191);

  /**
   * A standard paper size.
   */
  public static final PageSize A3_EXTRA = new PageSize(913, 1262);

  /**
   * A standard paper size.
   */
  public static final PageSize A3_EXTRATRANSVERSE = new PageSize(913, 1262);

  /**
   * A standard paper size.
   */
  public static final PageSize A3_ROTATED = new PageSize(1191, 842);

  /**
   * A standard paper size.
   */
  public static final PageSize A4 = new PageSize(595, 842);

  /**
   * A standard paper size.
   */
  public static final PageSize A4_TRANSVERSE = new PageSize(595, 842);

  /**
   * A standard paper size.
   */
  public static final PageSize A4_EXTRA = new PageSize(667, 914);

  /**
   * A standard paper size.
   */
  public static final PageSize A4_PLUS = new PageSize(595, 936);

  /**
   * A standard paper size.
   */
  public static final PageSize A4_ROTATED = new PageSize(842, 595);

  /**
   * A standard paper size.
   */
  public static final PageSize A4_SMALL = new PageSize(595, 842);

  /**
   * A standard paper size.
   */
  public static final PageSize A5 = new PageSize(420, 595);

  /**
   * A standard paper size.
   */
  public static final PageSize A5_TRANSVERSE = new PageSize(420, 595);

  /**
   * A standard paper size.
   */
  public static final PageSize A5_EXTRA = new PageSize(492, 668);

  /**
   * A standard paper size.
   */
  public static final PageSize A5_ROTATED = new PageSize(595, 420);

  /**
   * A standard paper size.
   */
  public static final PageSize A6 = new PageSize(297, 420);

  /**
   * A standard paper size.
   */
  public static final PageSize A6_ROTATED = new PageSize(420, 297);

  /**
   * A standard paper size.
   */
  public static final PageSize A7 = new PageSize(210, 297);

  /**
   * A standard paper size.
   */
  public static final PageSize A8 = new PageSize(148, 210);

  /**
   * A standard paper size.
   */
  public static final PageSize A9 = new PageSize(105, 148);

  /**
   * A standard paper size.
   */
  public static final PageSize A10 = new PageSize(73, 105);

  /**
   * A standard paper size.
   */
  public static final PageSize ANSIC = new PageSize(1224, 1584);

  /**
   * A standard paper size.
   */
  public static final PageSize ANSID = new PageSize(1584, 2448);

  /**
   * A standard paper size.
   */
  public static final PageSize ANSIE = new PageSize(2448, 3168);

  /**
   * A standard paper size.
   */
  public static final PageSize ARCHA = new PageSize(648, 864);

  /**
   * A standard paper size.
   */
  public static final PageSize ARCHB = new PageSize(864, 1296);

  /**
   * A standard paper size.
   */
  public static final PageSize ARCHC = new PageSize(1296, 1728);

  /**
   * A standard paper size.
   */
  public static final PageSize ARCHD = new PageSize(1728, 2592);

  /**
   * A standard paper size.
   */
  public static final PageSize ARCHE = new PageSize(2592, 3456);

  /**
   * A standard paper size.
   */
  public static final PageSize B0 = new PageSize(2920, 4127);

  /**
   * A standard paper size.
   */
  public static final PageSize B1 = new PageSize(2064, 2920);

  /**
   * A standard paper size.
   */
  public static final PageSize B2 = new PageSize(1460, 2064);

  /**
   * A standard paper size.
   */
  public static final PageSize B3 = new PageSize(1032, 1460);

  /**
   * A standard paper size.
   */
  public static final PageSize B4 = new PageSize(729, 1032);

  /**
   * A standard paper size.
   */
  public static final PageSize B4_ROTATED = new PageSize(1032, 729);

  /**
   * A standard paper size.
   */
  public static final PageSize B5 = new PageSize(516, 729);

  /**
   * A standard paper size.
   */
  public static final PageSize B5_TRANSVERSE = new PageSize(516, 729);

  /**
   * A standard paper size.
   */
  public static final PageSize B5_ROTATED = new PageSize(729, 516);

  /**
   * A standard paper size.
   */
  public static final PageSize B6 = new PageSize(363, 516);

  /**
   * A standard paper size.
   */
  public static final PageSize B6_ROTATED = new PageSize(516, 363);

  /**
   * A standard paper size.
   */
  public static final PageSize B7 = new PageSize(258, 363);

  /**
   * A standard paper size.
   */
  public static final PageSize B8 = new PageSize(181, 258);

  /**
   * A standard paper size.
   */
  public static final PageSize B9 = new PageSize(127, 181);

  /**
   * A standard paper size.
   */
  public static final PageSize B10 = new PageSize(91, 127);

  /**
   * A standard paper size.
   */
  public static final PageSize C4 = new PageSize(649, 918);

  /**
   * A standard paper size.
   */
  public static final PageSize C5 = new PageSize(459, 649);

  /**
   * A standard paper size.
   */
  public static final PageSize C6 = new PageSize(323, 459);

  /**
   * A standard paper size.
   */
  public static final PageSize COMM10 = new PageSize(297, 684);

  /**
   * A standard paper size.
   */
  public static final PageSize DL = new PageSize(312, 624);

  /**
   * A standard paper size.
   */
  public static final PageSize DOUBLEPOSTCARD = new PageSize(567, 419);  // should be 419.5, but I ignore that..

  /**
   * A standard paper size.
   */
  public static final PageSize DOUBLEPOSTCARD_ROTATED = new PageSize(419, 567);

  /**
   * A standard paper size.
   */
  public static final PageSize ENV9 = new PageSize(279, 639);

  /**
   * A standard paper size.
   */
  public static final PageSize ENV10 = new PageSize(297, 684);

  /**
   * A standard paper size.
   */
  public static final PageSize ENV11 = new PageSize(324, 747);

  /**
   * A standard paper size.
   */
  public static final PageSize ENV12 = new PageSize(342, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize ENV14 = new PageSize(360, 828);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC0 = new PageSize(2599, 3676);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC1 = new PageSize(1837, 2599);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC2 = new PageSize(1298, 1837);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC3 = new PageSize(918, 1296);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC4 = new PageSize(649, 918);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC5 = new PageSize(459, 649);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC6 = new PageSize(323, 459);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC65 = new PageSize(324, 648);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVC7 = new PageSize(230, 323);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVCHOU3 = new PageSize(340, 666);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVCHOU3_ROTATED = new PageSize(666, 340);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVCHOU4 = new PageSize(255, 581);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVCHOU4_ROTATED = new PageSize(581, 255);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVDL = new PageSize(312, 624);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVINVITE = new PageSize(624, 624);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVISOB4 = new PageSize(708, 1001);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVISOB5 = new PageSize(499, 709);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVISOB6 = new PageSize(499, 354);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVITALIAN = new PageSize(312, 652);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVELOPE = new PageSize(312, 652);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVKAKU2 = new PageSize(680, 941);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVKAKU2_ROTATED = new PageSize(941, 680);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVKAKU3 = new PageSize(612, 785);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVKAKU3_ROTATED = new PageSize(785, 612);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVMONARCH = new PageSize(279, 540);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPERSONAL = new PageSize(261, 468);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC1 = new PageSize(289, 468);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC1_ROTATED = new PageSize(468, 289);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC2 = new PageSize(289, 499);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC2_ROTATED = new PageSize(499, 289);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC3 = new PageSize(354, 499);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC3_ROTATED = new PageSize(499, 354);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC4 = new PageSize(312, 590);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC4_ROTATED = new PageSize(590, 312);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC5 = new PageSize(312, 624);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC5_ROTATED = new PageSize(624, 312);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC6 = new PageSize(340, 652);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC6_ROTATED = new PageSize(652, 340);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC7 = new PageSize(454, 652);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC7_ROTATED = new PageSize(652, 454);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC8 = new PageSize(340, 876);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC8_ROTATED = new PageSize(876, 340);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC9 = new PageSize(649, 918);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC9_ROTATED = new PageSize(918, 649);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC10 = new PageSize(918, 1298);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVPRC10_ROTATED = new PageSize(1298, 918);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVYOU4 = new PageSize(298, 666);

  /**
   * A standard paper size.
   */
  public static final PageSize ENVYOU4_ROTATED = new PageSize(666, 298);

  /**
   * A standard paper size.
   */
  public static final PageSize EXECUTIVE = new PageSize(522, 756);

  /**
   * A standard paper size.
   */
  public static final PageSize FANFOLDUS = new PageSize(1071, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize FANFOLDGERMAN = new PageSize(612, 864);

  /**
   * A standard paper size.
   */
  public static final PageSize FANFOLDGERMANLEGAL = new PageSize(612, 936);

  /**
   * A standard paper size.
   */
  public static final PageSize FOLIO = new PageSize(595, 935);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB0 = new PageSize(2835, 4008);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB1 = new PageSize(2004, 2835);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB2 = new PageSize(1417, 2004);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB3 = new PageSize(1001, 1417);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB4 = new PageSize(709, 1001);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB5 = new PageSize(499, 709);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB5_EXTRA = new PageSize(570, 782);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB6 = new PageSize(354, 499);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB7 = new PageSize(249, 354);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB8 = new PageSize(176, 249);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB9 = new PageSize(125, 176);

  /**
   * A standard paper size.
   */
  public static final PageSize ISOB10 = new PageSize(88, 125);

  /**
   * A standard paper size.
   */
  public static final PageSize LEDGER = new PageSize(1224, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize LEGAL = new PageSize(612, 1008);

  /**
   * A standard paper size.
   */
  public static final PageSize LEGAL_EXTRA = new PageSize(684, 1080);

  /**
   * A standard paper size.
   */
  public static final PageSize LETTER = new PageSize(612, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize LETTER_TRANSVERSE = new PageSize(612, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize LETTER_EXTRA = new PageSize(684, 864);

  /**
   * A standard paper size.
   */
  public static final PageSize LETTER_EXTRATRANSVERSE = new PageSize(684, 864);

  /**
   * A standard paper size.
   */
  public static final PageSize LETTER_PLUS = new PageSize(612, 914);

  /**
   * A standard paper size.
   */
  public static final PageSize LETTER_ROTATED = new PageSize(792, 612);

  /**
   * A standard paper size.
   */
  public static final PageSize LETTER_SMALL = new PageSize(612, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize MONARCH = ENVMONARCH;

  /**
   * A standard paper size.
   */
  public static final PageSize NOTE = new PageSize(612, 792);

  /**
   * A standard paper size.
   */
  public static final PageSize POSTCARD = new PageSize(284, 419);

  /**
   * A standard paper size.
   */
  public static final PageSize POSTCARD_ROTATED = new PageSize(419, 284);

  /**
   * A standard paper size.
   */
  public static final PageSize PRC16K = new PageSize(414, 610);

  /**
   * A standard paper size.
   */
  public static final PageSize PRC16K_ROTATED = new PageSize(610, 414);

  /**
   * A standard paper size.
   */
  public static final PageSize PRC32K = new PageSize(275, 428);

  /**
   * A standard paper size.
   */
  public static final PageSize PRC32K_ROTATED = new PageSize(428, 275);

  /**
   * A standard paper size.
   */
  public static final PageSize PRC32K_BIG = new PageSize(275, 428);

  /**
   * A standard paper size.
   */
  public static final PageSize PRC32K_BIGROTATED = new PageSize(428, 275);

  /**
   * A standard paper size.
   */
  public static final PageSize QUARTO = new PageSize(610, 780);

  /**
   * A standard paper size.
   */
  public static final PageSize STATEMENT = new PageSize(396, 612);

  /**
   * A standard paper size.
   */
  public static final PageSize SUPERA = new PageSize(643, 1009);

  /**
   * A standard paper size.
   */
  public static final PageSize SUPERB = new PageSize(864, 1380);

  /**
   * A standard paper size.
   */
  public static final PageSize TABLOID = new PageSize(792, 1224);

  /**
   * A standard paper size.
   */
  public static final PageSize TABLOIDEXTRA = new PageSize(864, 1296);

  /**
   * The width of the page in point.
   */
  private double width;
  /**
   * The height of the page in point.
   */
  private double height;

  /**
   * Creates a new page-size object with the given width and height.
   *
   * @param width  the width in point.
   * @param height the height in point.
   */
  public PageSize(final double width, final double height)
  {
    this.width = width;
    this.height = height;
  }

  /**
   * Returns the page's width.
   *
   * @return the width in point.
   */
  public double getWidth()
  {
    return width;
  }

  /**
   * Returns the page's height.
   *
   * @return the height in point.
   */
  public double getHeight()
  {
    return height;
  }

  /**
   * Compares this page size with the given object.
   *
   * @param o the other object.
   * @return true, if the given object is also a PageSize object and has the same width and height, false otherwise.
   */
  public boolean equals(final Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    final PageSize pageSize = (PageSize) o;


    if (equal(pageSize.height, height) == false)
    {
      return false;
    }
    if (equal(pageSize.width, width) == false)
    {
      return false;
    }

    return true;
  }

  /**
   * An internal helper method that compares two doubles for equality.
   * @param d1 the one double.
   * @param d2 the other double.
   * @return true, if both doubles are binary equal, false otherwise.
   */
  private boolean equal(final double d1, final double d2)
  {
    return Double.doubleToLongBits(d1) == Double.doubleToLongBits(d2);
  }

  /**
   * Computes a hashcode for this page-size.
   *
   * @return the hashcode.
   */
  public int hashCode()
  {
    long temp = width != +0.0d ? Double.doubleToLongBits(width) : 0L;
    int result = (int) (temp ^ (temp >>> 32));
    temp = height != +0.0d ? Double.doubleToLongBits(height) : 0L;
    result = 29 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
