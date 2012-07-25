#!/bin/bash
# build_wfdbjava.sh
# download and build all of the native libraries to support wfdb;
# only tested on gnu/linux



if [ ! -n "$1" ] || [ ! -n "$2" ]
then
    echo "Usage: $0 <install_dir>"
fi

# FIXME: the version number changes!
WFDB_VER="wfdb-10.5.13"

SRC_LOCATION="/home/ikaro/workspace/wfdb-java/nativelibs/tarballs/"
cd $1
mkdir tmp
cd tmp

#
# build libgpg-error
#
#WGET may not work, depending on firewall settings
#wget ftp://ftp.gnupg.org/gcrypt/libgpg-error/libgpg-error-1.10.tar.bz2
cp $SRC_LOCATIONlibgpg-error-1.10.tar.bz2 $1/tmp/
tar xvfj libgpg-error-1.10.tar.bz2
cd libgpg-error-1.10
./configure --prefix=$1
make
make install
cd ..


#
# build libidn
#
#wget ftp://aeneas.mit.edu/pub/gnu/libidn/libidn-1.12.tar.gz
cp $SRC_LOCATIONlibidn-1.12.tar.gz $1/tmp/
tar xvfz libidn-1.12.tar.gz
cd libidn-1.12
./configure --prefix=$1
make
make install
cd ..


#
# build libgcrypt
#
#wget ftp://ftp.gnupg.org/gcrypt/libgcrypt/libgcrypt-1.4.4.tar.bz2
cp $SRC_LOCATIONlibgcrypt-1.4.4.tar.bz2 $1/tmp/
tar xvfj libgcrypt-1.4.4.tar.bz2
cd libgcrypt-1.4.4
./configure --prefix=$1 --with-gpg-error-prefix=$1
make
make install
cd ..


#
# build gnutls
#
#wget http://ftp.gnu.org/pub/gnu/gnutls/gnutls-2.6.4.tar.bz2
cp $SRC_LOCATIONgnutls-2.6.4.tar.bz2 $1/tmp/
tar xvfj gnutls-2.6.4.tar.bz2
cd gnutls-2.6.4
./configure --prefix=$1 --with-libgcrypt-prefix=$1 --without-zlib
make
make install
cd ..


#
# build curl
#
#wget http://curl.haxx.se/download/curl-7.19.3.tar.bz2
cp $SRC_LOCATIONcurl-7.19.3.tar.bz2 $1/tmp/
tar xvfj curl-7.19.3.tar.bz2
cd curl-7.19.3
./configure --prefix=$1 --with-gnutls=$1 --with-libidn=$1 \
            --without-ssl --without-zlib --without-libssh2 --disable-ldap
make
make install
cd ..


#
# build wfdb
#wget http://www.physionet.org/physiotools/wfdb.tar.gz
cp $SRC_LOCATIONwfdb.tar.gz $1/tmp/
tar xvfz wfdb.tar.gz
cd $WFDB_VER
PATH=$1/bin:$PATH ./configure --prefix=$1 --with-libcurl
PATH=$1/bin:$PATH make
PATH=$1/bin:$PATH make install
cd ..



#
# build edr
#
cc -o $1/bin/edr $SRC_LOCATIONedr.c -lm -lwfdb

#
# build dfa
#
cc -o $1/bin/dfa $SRC_LOCATIONdfa.c -lm 
