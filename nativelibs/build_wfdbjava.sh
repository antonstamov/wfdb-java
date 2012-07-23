#!/bin/bash
# build_wfdbjava.sh
# download and build all of the native libraries to support wfdbjava;
# only tested on gnu/linux
# FIXME: include -fno-stack-protector


#
#
#
if [ ! -n "$1" ] || [ ! -n "$2" ]
then
    echo "Usage: $0 <install_dir> <java_home>"
fi

JAVA_HOME="$2"


#
#
#
cd $1
mkdir tmp
cd tmp


#
# build libgpg-error
#
wget ftp://ftp.gnupg.org/gcrypt/libgpg-error/libgpg-error-1.7.tar.bz2
tar xvfj libgpg-error-1.7.tar.bz2
cd libgpg-error-1.7
./configure --prefix=$1
make
make install
cd ..


#
# build libidn
#
wget ftp://aeneas.mit.edu/pub/gnu/libidn/libidn-1.12.tar.gz
tar xvfz libidn-1.12.tar.gz
cd libidn-1.12
./configure --prefix=$1
make
make install
cd ..


#
# build libgcrypt
#
wget ftp://ftp.gnupg.org/gcrypt/libgcrypt/libgcrypt-1.4.4.tar.bz2
tar xvfj libgcrypt-1.4.4.tar.bz2
cd libgcrypt-1.4.4
./configure --prefix=$1 --with-gpg-error-prefix=$1
make
make install
cd ..


#
# build gnutls
#
wget http://ftp.gnu.org/pub/gnu/gnutls/gnutls-2.6.4.tar.bz2
tar xvfj gnutls-2.6.4.tar.bz2
cd gnutls-2.6.4
./configure --prefix=$1 --with-libgcrypt-prefix=$1 --without-zlib
make
make install
cd ..


#
# build curl
#
wget http://curl.hoxt.com/download/curl-7.19.3.tar.bz2
tar xvfj curl-7.19.3.tar.bz2
cd curl-7.19.3
./configure --prefix=$1 --with-gnutls=$1 --with-libidn=$1 \
            --without-ssl --without-zlib --without-libssh2 --disable-ldap
make
make install
cd ..


#
# build wfdb
# FIXME: the version number changes!
#
wget http://www.physionet.org/physiotools/wfdb.tar.gz
tar xvfz wfdb.tar.gz
cd wfdb-10.4.17
PATH=$1/bin:$PATH ./configure --prefix=$1 --with-libcurl
PATH=$1/bin:$PATH make
PATH=$1/bin:$PATH make install
cd ..
