/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

/**
 *  Connecting object used in turntable config to allow defining if port is an input
 * @author Chris
 */
  public class TurntableConnector {
        ConnectionInterface port;
        Boolean input;
        
        public TurntableConnector(ConnectionInterface port, Boolean input) {
            this.port = port;
            this.input = input;
        }
    }