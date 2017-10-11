/*
Plugin of Miranda IM for communicating with users of the MSN Messenger protocol.
Copyright (c) 2003-5 George Hazan.
Copyright (c) 2002-3 Richard Hughes (original version).

Miranda IM: the free icq client for MS Windows
Copyright (C) 2000-2002 Richard Hughes, Roland Rabien & Tristan Van de Vreede

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

#include <stdio.h>
#include <string.h>

#include "msn_global.h"

int maybekanji(int c1, int c2);
int rjis(char *t, char *s);
unsigned short Jis2Sjis( unsigned short jis );
void JIS2SJIS( char* text );
//void JIStoSJIS( unsigned char knj1, unsigned char knj2 );
void JIStoSJIS( unsigned char *knj1, unsigned char *knj2 );
void jiskarasjis(char *trg, char *str);

/*
 *  MIME decoding routines
 *
 *	Written by S. Ichikawa,
 *	partially inspired by encdec.c of <jh@efd.lth.se>.
 */
#define	BUFLEN	1024
#define ESC	(0x1b)


#define ASCII    0x00
#define EUC      0x01
#define SJIS     0x02
#define JIS      0x04
#define JAPANESE 0xff


static char mm64[] =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=" ;
static char mmquote[] = "0123456789ABCDEF";
static int mmcont = 0;

void mmdec_base64( char *t, char *s)
{
	int   d, count, j, val;
	char  buf[BUFLEN], *bp, nw[4], *p;

	for (bp = buf; *s; s += 4)
	{
		val = 0;
		if (s[2] == '=') count = 1;
		else if (s[3] == '=') count = 2;
		else count = 3;

		for (j = 0; j <= count; j++)
		{
			if (!(p = strchr(mm64, s[j])))
				return;

			d = p - mm64;
			d <<= (3-j)*6;
			val += d;
		}

		for (j = 2; j >= 0; j--)
		{
			nw[j] = val & 255;
			val >>= 8;
		}
		if (count--) *bp++ = nw[0];
		if (count--) *bp++ = nw[1];
		if (count) *bp++ = nw[2];
	}
	*bp = '\0';
	strcpy(t, buf);
}

void mmdec_quote( char *t, char *s )
{
	char  buf[BUFLEN], cval, *bp, *p;

	for ( bp = buf; *s; )
	{
		if (*s == '=')
		{
			cval = 0;
			if (s[1] && (p = strchr(mmquote, s[1])))
				cval += (p - mmquote);
			else
			{	*bp++ = *s++;
				continue;
			}

			if (s[2] && (p = strchr(mmquote, s[2])))
			{
				cval <<= 4;
				cval += (p - mmquote);
				*bp++ = cval;
				s += 3;
			}
			else *bp++ = *s++;
		}
		else if (*s == '_')
		{
			*bp++ = 0x20;
			s++;
		}
		else *bp++ = *s++;
	}

	*bp = '\0';
	strcpy( t, buf );
}

void mmdecode( char *trg, char *str )
{
	char buf[BUFLEN], mmbuf[BUFLEN];
	char *s, *t, *u;
	int  base64, quote;
	int  jis = 0;
	char *c;

	buf[0] = '\0';

	for (s = str, u = buf; *s; )
	{
		//	if (!strnicmp(s, "=?ISO-2022-JP?B?", 16) || !strnicmp(s, "=?iso-2022-jp?B?", 16)) {
		//	if (!strncasecmp(s, "=?ISO-2022-JP?B?", 16)) {
		if ( strstr(s, "=?") && strstr(s, "?B?" ))
			base64 = 1;
		else
			base64 = 0;

		//	if (!strncasecmp(s, "=?ISO-2022-JP?Q?", 16)) {
		//	if (!strnicmp(s, "=?ISO-2022-JP?Q?", 16) || !strnicmp(s, "=?iso-2022-jp?Q?", 16)){
		if ( strstr( s, "=?" ) && strstr( s, "?Q?" ))
			quote = 1;
		else
			quote = 0;

		if ( strstr(s, "ISO-2022-JP") || strstr( s, "iso-2022-jp" ))
			jis = 1;
		else
			jis = 0;

		if ( base64 || quote )
		{
			if ( mmcont )
			{
				for ( t = s - 1; t >= str && (*t == ' ' || *t == '\t'); t--)
					u--;
			}
			if(quote) c = strstr(s,"?Q?");
			if(base64) c = strstr(s,"?B?");

			for (s = c+3, t = mmbuf; *s; )
			{
				if (s[0] == '?' && s[1] == '=')
					break;

				*t++ = *s++;
			}

			if ( s[0] != '?' || s[1] != '=' )
				goto end;

			s += 2;
			*t = '\0';

			if (base64) mmdec_base64(mmbuf, mmbuf);
			if (quote) mmdec_quote(mmbuf, mmbuf);
			for ( t = mmbuf; *t; )
				*u++ = *t++;

			mmcont = 1;
			/* if (*s == ' ' || *s == '\t') *u++ = *s; */
			/* for ( ; *s == ' ' || *s == '\t'; s++) ; */
		}
		else
		{
			if (*s != ' ' && *s != '\t') mmcont = 0;
			*u++ = *s++;
	}	}

	*u = '\0';
end:
	if ( jis )
		jiskarasjis( buf, buf ); // japanese jis code to shift-jis code
	strcpy( trg, buf );
}

void jiskarasjis( char *trg, char *str )
{
    char buf[BUFLEN];

    strcpy(buf,str);
    rjis(buf, buf);

    JIS2SJIS(buf);

    strcpy(trg, buf);
}

/*
 *  Insert ESC where it seems lost.
 *  (The author of this function "rjis" is S. Ichikawa.)
 */

int rjis( char *t, char *s )
{
	char *p, buf[BUFLEN];
	int kanji = 0;

	if (strchr(s, ESC) || !strchr(s, '$'))
	{
		if (s != t) strcpy(t, s);
		return 1;
	}

	for (p = buf; *s; )
	{
		if (!kanji && s[0] == '$' && (s[1] == '@' || s[1] == 'B'))
		{
			if (maybekanji((int)s[2], (int)s[3]))
			{
				kanji = 1;
				*p++ = ESC;
				*p++ = *s++;
				*p++ = *s++;
				*p++ = *s++;
				*p++ = *s++;
				continue;
			}
			*p++ = *s++;
			continue;
		}
		if (kanji && s[0] == '(' && (s[1] == 'J' || s[1] == 'B'))
		{
			kanji = 0;
			*p++ = ESC;
			*p++ = *s++;
			*p++ = *s++;
			continue;
		}
		*p++ = *s++;
	}
	*p = *s;    /* terminate string */

	strcpy(t, buf);
	return 0;
}

/*
 * The following function "maybekanji" is derived from
 * RJIS-1.0 by Mr. Hironobu Takahashi.
 * Maybekanji() is included here under the courtesy of the author.
 * The original comment of rjis.c is also included here.
 */

/*
 * RJIS ( Recover JIS code from broken file )
 * Copyright (C) 1992 1994
 * Hironobu Takahashi (takahasi@tiny.or.jp)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either versions 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SKK, see the file COPYING.  If not, write to the Free
 * Software Foundation Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

int maybekanji( int c1, int c2 )
{
    if ((c2 < 33) || (c2 > 126)) return 0;
    if ((c1 < 33) || ((40 < c1) && (c1 < 48)) || (116 < c1)) return 0;
    c2 -= 32;
    switch(c1-32) {
      case 2:
	if ((14 < c2) && ( c2 < 26)) return 0;
	if ((33 < c2) && ( c2 < 42)) return 0;
	if ((48 < c2) && ( c2 < 60)) return 0;
	if ((74 < c2) && ( c2 < 82)) return 0;
	if ((89 < c2) && ( c2 < 94)) return 0;
	break;
      case 3:
	if (c2 < 16) return 0;
	if ((25 < c2) && ( c2 < 33)) return 0;
	if ((58 < c2) && ( c2 < 65)) return 0;
	if (90 < c2) return 0;
	break;
      case 4:
	if (83 < c2) return 0;
	break;
      case 5:
	if (86 < c2) return 0;
	break;
      case 6:
	if ((24 < c2) && ( c2 < 33)) return 0;
	if (56 < c2) return 0;
	break;
      case 7:
	if ((33 < c2) && ( c2 < 49)) return 0;
	if (81 < c2) return 0;
	break;
      case 8:
	if (32 < c2) return 0;
	break;
      case 47:
	if (51 < c2) return 0;
	break;
      case 84:
	if (6 < c2) return 0;
	break;
    }
    return 1;
}

unsigned int jis2sjis(unsigned int jis)
{
	unsigned int hib, lob;

	hib = (jis >> 8) & 0xff;
	lob = jis & 0xff;
	lob += (hib & 1) ? 0x1f : 0x7d;
	if (lob >= 0x7f) lob++;
	hib = ((hib - 0x21) >> 1) + 0x81;
	if (hib > 0x9f) hib += 0x40;

	return (hib << 8) | lob;
}

static void rint2hexsz(char *str, int num, int *off)
{
	int k, n;

	if ((k = num >> 4) != 0)
		rint2hexsz(str, k, off);

	n = num & 0xf;
	*(str + *off) = n <= 9 ? n + '0' : n - 10 + 'A';
	(*off)++;
}

void int2hexsz(char *str, int num)
{
	int i;

	i = 0;
	if (num < 0) {
		num = -num;
		*str = '-';
		i++;
	}
	rint2hexsz(str, num, &i);
	*(str + i) = '\0';
}

int hexsz2int(char *str)
{
	int val;
	int sign;

	while (*str == ' ' || *str == '\t') str++;
	sign = 1;
	if (*str == '+') str++;
	else if (*str == '-') {
		sign = -1;
		str++;
	}
	val = 0;
	while (*str >= '0' && *str <= '9' ||
		*str >= 'A' && *str <= 'F' ||
		*str >= 'a' && *str <= 'f') {
		val <<= 4;
		if      (*str >= '0' && *str <= '9') val += *str - '0';
		else if (*str >= 'A' && *str <= 'F') val += *str - 'A' + 10;
		else                                 val += *str - 'a' + 10;
		str++;
	}

	return sign == 1 ? val : -val;
}

unsigned short Jis2Sjis( unsigned short jis )
{
	unsigned short ubyte, lbyte;

	ubyte = jis >> 8;
	lbyte = jis & 0x00ff;

	lbyte += 0x1f;
	if ( lbyte >= 0x7f ) lbyte++;
	if ( lbyte <= 0x3f ) return 0;

	if ( (ubyte & 0x0001) == 0 )
	{
		lbyte = jis & 0x00ff;
		lbyte += 0x7e;
		ubyte--;
		if ( lbyte > 0xfd ) return 0;
	}

	ubyte -= 0x1f;
	ubyte = ubyte >> 1;
	ubyte += 0x80;
	if ( ubyte >= 0xa0 ) ubyte += 0x40;

	if ( ((ubyte >= 0x81) && (ubyte <= 0x9f)) || ((ubyte >= 0xe0) && (ubyte <= 0xef)) )
		return (ubyte << 8) + lbyte;

	return 0;
}

//---- JIS文字をSJIS文字に変換する関数
//inline
void JIStoSJIS(unsigned char *knj1, unsigned char *knj2 )
{
  if( *knj1 & 0x01 ){
    *knj1 >>= 1;
    if( *knj1 < 0x2F ) *knj1 += 0x71; else *knj1 -= 0x4F;
    if( *knj2 > 0x5F ) *knj2 += 0x20; else *knj2 += 0x1F;
  }else{
    *knj1 >>= 1;
    if( *knj1 < 0x2F ) *knj1 += 0x70; else *knj1 -= 0x50;
    *knj2 += 0x7E;
  }
}



void JIS2SJIS( char* text ) {
  int mode = ASCII;

  unsigned char  *wr, *re;
  for( wr=re=(unsigned char*)text; *re; re++ ){
    if( (re[0]=='\x1b' && re[1]=='$' && re[2] == 'B' ) ||
        (re[0]=='\x1b' && re[1]=='$' && re[2] == '@' ) ){
      re+=2;
      mode = JAPANESE;
      continue;
    }else if( (re[0]=='\x0f') ||
        (re[0]=='\x1b' && re[1]=='(' && re[2] == 'B' ) ||
        (re[0]=='\x1b' && re[1]=='(' && re[2] == 'J' ) ){
      re+=2;
      mode = ASCII;
      continue;
    }else if( (re[0]=='\x0e') ||
        (re[0]=='\x1b' && re[1]=='(' && re[2] == 'I' ) ){
      re+=2;
      mode = ASCII; // hankaku IGNORE
      continue;
    }

    if( mode == ASCII ){
      *wr++ = *re;
      continue;
    }
    *wr++ = *re;
    if( !(*wr = *++re) ) break;
//    JIStoSJIS( *(wr-1), *wr );
    JIStoSJIS( (wr-1), wr );
    wr++;
  }
  *wr='\0';
}
