import React from 'react';

export default function Avatar(props) {

    const initials = props.name
    .split(' ')
    .map((word:string) => word[0])
    .join('')
    .toUpperCase();

    const generateRandomColor = () => {
        const letters = '0123456789ABCDEF';
        let color = '#';
        for (let i = 0; i < 6; i++) {
          color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
      };
    
      const backgroundColor = generateRandomColor();

   
  return (
    <div
      style={{
        width: '40px',
        height: '40px',
        borderRadius: '50%',
        backgroundColor: backgroundColor,
        color: '#fff',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        fontSize: '16px',
        fontWeight: 'bold',
      }}
    >
      {initials}
    </div>
  );
  }

// const Avatar = ({ name }) => {
//   // Extract initials
//   const initials = name
//     .split(' ')
//     .map((word) => word[0])
//     .join('')
//     .toUpperCase();

//   // Generate a random background color
//   const generateRandomColor = () => {
//     const letters = '0123456789ABCDEF';
//     let color = '#';
//     for (let i = 0; i < 6; i++) {
//       color += letters[Math.floor(Math.random() * 16)];
//     }
//     return color;
//   };

//   const backgroundColor = generateRandomColor();

//   return (
//     <div
//       style={{
//         width: '40px',
//         height: '40px',
//         borderRadius: '50%',
//         backgroundColor: backgroundColor,
//         color: '#fff',
//         display: 'flex',
//         alignItems: 'center',
//         justifyContent: 'center',
//         fontSize: '16px',
//         fontWeight: 'bold',
//       }}
//     >
//       {initials}
//     </div>
//   );
// };

// export default Avatar;