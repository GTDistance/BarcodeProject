package com.thomas.beans;

public class QRCodeBean {

    public QRCodeBean() {
    }

    public QRCodeBean(String fileName, String[] pileGunCodes) {
            this.fileName = fileName;
            this.pileGunCodes = pileGunCodes;
        }

        private String fileName;
        private String[] pileGunCodes;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String[] getPileGunCodes() {
            return pileGunCodes;
        }

        public void setPileGunCodes(String[] pileGunCodes) {
            this.pileGunCodes = pileGunCodes;
        }
    }