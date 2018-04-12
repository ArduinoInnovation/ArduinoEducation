import React from 'react'
import styles from './index.css'

export default ({submitEvent})=>(
  <header style={{
    position: 'fixed',
    right: 0,
    left: 0,
    top: 0,
    boxShadow: '0 1px 15px rgba(0, 0, 0, 0.034)',
    height: '63px',
    backgroundColor: 'white'}}>
    <div className={styles["my-name"]}>
      <a href="https://github.com/ArduinoInnovation/ArduinoEducation">Arduino</a>
    </div>
    <div onClick={submitEvent} className={styles["main-nav-container"]}>
      SUBMIT
    </div>
  </header>
)
