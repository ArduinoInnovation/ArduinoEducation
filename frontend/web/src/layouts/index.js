import React from 'react';
import styles from './index.css';
import withRouter from 'umi/withRouter';

function Layout({ children, location }) {
  return (
    <div className={styles.normal}>
      <div className={styles.content}>
        <div className={styles.main}>
          <div
            style={{
              minHeight:"100vh",
              background:"white"
           }}
          >
            {children}</div>
        </div>
      </div>
    </div>
  );
}

export default withRouter(Layout);
