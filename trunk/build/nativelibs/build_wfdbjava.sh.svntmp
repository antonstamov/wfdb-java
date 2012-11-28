#!/bin/bash
# build_wfdbjava.sh
# download and build all of the native libraries to support wfdb;
# only tested on gnu/linux



if [ ! -n "$1" ] || [ ! -n "$2" ]
then
    echo "Usage: $0 <install_dir> <target>"
fi

# FIXME: the version number changes!
WFDB_VER="wfdb-10.5.13"
CUR_DIR=`pwd`
SRC_LOCATION=${CUR_DIR}"/tarballs/"
cd $1
mkdir tmp
cd tmp

if [ ${2} = "libgpg" ]; then
#
# build libgpg-error
#
#WGET may not work, depending on firewall settings
#wget ftp://ftp.gnupg.org/gcrypt/libgpg-error/libgpg-error-1.10.tar.bz2
cp ${SRC_LOCATION}libgpg-error-1.10.tar.bz2 $1/tmp/
tar xvfj libgpg-error-1.10.tar.bz2
cd libgpg-error-1.10
./configure --prefix=$1
make
make install
cd ..
fi

if [ ${2} = "libidn" ]; then
#
# build libidn
#
#wget ftp://aeneas.mit.edu/pub/gnu/libidn/libidn-1.12.tar.gz
cp ${SRC_LOCATION}libidn-1.12.tar.gz $1/tmp/
tar xvfz libidn-1.12.tar.gz
cd libidn-1.12
./configure --prefix=$1
make
make install
cd ..
fi

if [ ${2} = "libgcrypt" ]; then
#
# build libgcrypt
#
#wget ftp://ftp.gnupg.org/gcrypt/libgcrypt/libgcrypt-1.4.4.tar.bz2
cp ${SRC_LOCATION}libgcrypt-1.4.4.tar.bz2 $1/tmp/
tar xvfj libgcrypt-1.4.4.tar.bz2
cd libgcrypt-1.4.4
./configure --prefix=$1 --with-gpg-error-prefix=$1
make
make install
cd ..
fi

if [ ${2} = "gnutls" ]; then
#
# build gnutls
#
#wget http://ftp.gnu.org/pub/gnu/gnutls/gnutls-2.6.4.tar.bz2
cp ${SRC_LOCATION}gnutls-2.6.4.tar.bz2 $1/tmp/
tar xvfj gnutls-2.6.4.tar.bz2
cd gnutls-2.6.4
./configure --prefix=$1 --with-libgcrypt-prefix=$1 --without-zlib
make
make install
cd ..
fi

if [ ${2} = "curl" ]; then
#
# build curl
#
#wget http://curl.haxx.se/download/curl-7.19.3.tar.bz2
cp ${SRC_LOCATION}curl-7.19.3.tar.bz2 $1/tmp/
tar xvfj curl-7.19.3.tar.bz2
cd curl-7.19.3
./configure --prefix=$1 --with-gnutls=$1 --with-libidn=$1 \
            --without-ssl --without-zlib --without-libssh2 --disable-ldap
make
make install
cd ..

fi


if [ ${2} = "wfdb" ]; then

#
# build wfdb
#wget http://www.physionet.org/physiotools/wfdb.tar.gz
cp ${SRC_LOCATION}wfdb.tar.gz $1/tmp/
tar xvfz wfdb.tar.gz
cd $WFDB_VER
PATH=$1/bin:$PATH ./configure --prefix=$1 --with-libcurl
PATH=$1/bin:$PATH make
PATH=$1/bin:$PATH make install
cd ..

fi

if [ ${2} = "edr" ]; then
#
# build edr
#
cc -o $1/bin/edr ${SRC_LOCATION}edr.c -lm -lwfdb
fi

if [ ${2} = "dfa" ]; then
#
# build dfa
#
cc -o $1/bin/dfa ${SRC_LOCATION}dfa.c -lm 
fi